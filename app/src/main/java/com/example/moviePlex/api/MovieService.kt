package com.example.moviePlex.api

import com.example.moviePlex.model.cast.Cast
import com.example.moviePlex.model.image.Image
import com.example.moviePlex.model.movie.Movie
import com.example.moviePlex.model.moviedetails.MovieDetails
import com.example.moviePlex.model.news.News
import com.example.moviePlex.model.video.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService
{
    //now_playing
    @GET("/3/movie/now_playing")
    fun getNowPlaying(@Query("api_key") api_key : String) : Call<Movie>

    //upcoming
    @GET("/3/movie/upcoming")
    fun getUpcoming(@Query("api_key") api_key : String) : Call<Movie>

    //popular
    @GET("/3/movie/popular")
    fun getPopular(@Query("api_key") api_key : String) : Call<Movie>

    //topRated
    @GET("/3/movie/top_rated")
    fun getTopRated(@Query("api_key") api_key : String) : Call<Movie>

//    DetailsMovie
    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movie_id : Int, @Query("api_key") api_key : String) : Call<MovieDetails>

//    search
    @GET("/3/search/movie")
    fun getSearch(@Query("api_key") api_key: String ,@Query("query") query : String , @Query("include_adult") include_adult : Boolean) : Call<Movie>

//    Video
    @GET("/3/movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") movie_id: Int , @Query("api_key") api_key : String) : Call<Video>

//    Image
    @GET("/3/movie/{movie_id}/images")
    fun getImage(@Path("movie_id") movie_id: Int , @Query("api_key") api_key : String) : Call<Image>

//    Cast
    @GET("/3/movie/{movie_id}/credits")
    fun getCast(@Path("movie_id") movie_id: Int , @Query("api_key") api_key : String) : Call<Cast>


    @GET("/news")
    fun getNews(@Query("category") category : String) : Call<News>

}