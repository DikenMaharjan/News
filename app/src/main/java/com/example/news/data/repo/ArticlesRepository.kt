package com.example.news.data.repo

import androidx.paging.*
import com.example.news.data.database.dao.ArticlesDao
import com.example.news.data.models.domain.Article
import com.example.news.data.models.local.ArticleEntity
import com.example.news.data.models.mappers.toEntity
import com.example.news.data.models.mappers.toModel
import com.example.news.data.models.response.fetchnewsresponse.FetchNewsResponse
import com.example.news.data.remote.ArticlesApi
import com.example.news.data.repo.ArticlesRepository.*
import com.example.news.network.Resource
import com.example.news.network.SafeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ArticlesRepository"

@Singleton
class ArticlesRepository @Inject constructor(
    private val articlesApi: ArticlesApi,
    private val safeApiCall: SafeApiCall,
    private val articlesDao: ArticlesDao
) {
    // These are the categories available in the API
    enum class AvailableCategories(val title: String) {
        General("General"),
        Business("Business"),
        Entertainment("Entertainment"),
        Health("Health"),
        Science("Science"),
        Sports("Sports"),
        Technology("Technology")
    }

    sealed class FetchArticlesFor(val fetchKey: String) {
        object Top : FetchArticlesFor("Top")
        class Category(
            val category: AvailableCategories
        ) : FetchArticlesFor(category.title)
    }

    fun getArticleFlow(articleURL: String): Flow<Article?> {
        return articlesDao.getArticle(articleURL).map {
            it?.toModel()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getArticlesPagingData(
        pageSize: Int = 30,
        fetchFor: FetchArticlesFor
    ): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize),
            remoteMediator = getRemoteMediator(
                pageSize = pageSize,
                fetchFor = fetchFor
            ),
            pagingSourceFactory = {
                articlesDao.getArticlesPagingSource(
                    fetchFor.fetchKey
                )
            }
        ).flow.mapTo {
            it.toModel()
        }
    }

    private fun <T : Any, V : Any> Flow<PagingData<T>>.mapTo(transform: (T) -> V): Flow<PagingData<V>> {
        return this.map { pagingData ->
            pagingData.map(transform)
        }
    }


    @OptIn(ExperimentalPagingApi::class)
    private fun getRemoteMediator(
        pageSize: Int,
        fetchFor: FetchArticlesFor
    ): RemoteMediator<Int, ArticleEntity> {
        return object : RemoteMediator<Int, ArticleEntity>() {
            override suspend fun load(
                loadType: LoadType,
                state: PagingState<Int, ArticleEntity>
            ): MediatorResult {
                val page = when (loadType) {
                    LoadType.REFRESH -> 1
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> articlesDao.getArticlesCount(
                        fetchFor.fetchKey
                    ) / pageSize + 1
                }
                val response = getArticles(
                    fetchFor = fetchFor,
                    page = page,
                    pageSize = pageSize
                )
                return response.getMediatorResult(
                    loadType = loadType,
                    pageSize = pageSize,
                    page = page,
                    fetchFor = fetchFor
                )
            }
        }
    }

    private suspend fun getArticles(
        fetchFor: FetchArticlesFor,
        page: Int,
        pageSize: Int
    ): Resource<FetchNewsResponse> {
        return safeApiCall.execute {
            when (fetchFor) {
                is FetchArticlesFor.Category -> {
                    articlesApi.getArticlesFromCategory(
                        category = fetchFor.category.title,
                        page = page,
                        pageSize = pageSize
                    )
                }
                FetchArticlesFor.Top -> {
                    articlesApi.getTopArticles(
                        page = page,
                        pageSize = pageSize
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalPagingApi::class)
    private suspend fun Resource<FetchNewsResponse>.getMediatorResult(
        loadType: LoadType,
        page: Int,
        pageSize: Int,
        fetchFor: FetchArticlesFor
    ) = when (this) {
        is Resource.Failure -> RemoteMediator.MediatorResult.Error(
            Exception(this.errorMsg)
        )
        is Resource.Success -> {
            if (loadType == LoadType.REFRESH) {
                articlesDao.deleteArticlesFetchedFor(fetchedFor = fetchFor.fetchKey)
            }
            articlesDao.storeArticles(
                this.data.articles.map {
                    it.toEntity(
                        fetchedFor = fetchFor.fetchKey
                    )
                }
            )
            RemoteMediator.MediatorResult.Success(
                endOfPaginationReached = page * pageSize >= this.data.totalResults
            )
        }
    }
}