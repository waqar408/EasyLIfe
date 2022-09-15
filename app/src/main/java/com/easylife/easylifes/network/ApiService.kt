package com.tabadol.tabadol.data.network

import com.easylife.easylifes.model.BaseResponse
import com.easylife.easylifes.model.allclients.AllClientsResponseModel
import com.easylife.easylifes.model.allworkouts.AllWorkoutsResponseModel
import com.easylife.easylifes.model.categorytrainer.CategoryTrainerResponseModel
import com.easylife.easylifes.model.categoryvideos.CategoryVideosResponseModel
import com.easylife.easylifes.model.chat.inbox.MessagesResponseModel
import com.easylife.easylifes.model.forgotpassword.ForgotPasswordResponseModel
import com.easylife.easylifes.model.home.HomeResponseModel
import com.easylife.easylifes.model.chat.messenger.MessengerResponseModel
import com.easylife.easylifes.model.getuserworkouts.GetUserWorkoutsResponseModel
import com.easylife.easylifes.model.mealplan.CreateMealPlanResponseModel
import com.easylife.easylifes.model.mealplan.MealPlanResponseModel
import com.easylife.easylifes.model.mealtime.MealTimesResponseModel
import com.easylife.easylifes.model.search.SearchResponseModel
import com.easylife.easylifes.model.signup.SignupResponseModel
import com.easylife.easylifes.model.trainerdetail.TrainerDetailResponseModel
import com.easylife.easylifes.model.trainerhome.TrainerHomeResponseModel
import com.easylife.easylifes.model.userworkoutcategories.UserCategoryDataModel
import com.easylife.easylifes.model.userworkoutcategories.UserCategoryResponseModel
import com.easylife.easylifes.model.verifydata.VerifyDataResponseModel
import com.easylife.easylifes.model.workoutdetial.UserWorkoutDetailResponseModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @POST("verify-data")
    @FormUrlEncoded
    fun verifyData(
        @Field("email") email: String,
        @Field("country_code") country_code: String?,
        @Field("phone") phone: String
    ):
            Call<VerifyDataResponseModel>


    @Headers("Accept: application/json")
    @POST("signup")
    @FormUrlEncoded
    fun signUp(
        @Field("signup_as") signup_as: String,
        @Field("name") name: String?,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("experience") experience: String,
        @Field("country_code") country_code: String,
        @Field("phone") phone: String
    ):
            Call<SignupResponseModel>


    @Headers("Accept: application/json")
    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String
    ):
            Call<SignupResponseModel>

    @Headers("Accept: application/json")
    @POST("update-profile")
    @FormUrlEncoded
    fun updateProfile(
        @Field("user_id") user_id: String,
        @Field("name") name: String?,
        @Field("username") username: String,
        @Field("country_code") country_code: String,
        @Field("phone") phone: String,
        @Field("gender") gender: String,
        @Field("age") age: String,
        @Field("weight") weight: String,
        @Field("weight_unit") weight_unit: String,
        @Field("height") height: String,
        @Field("height_unit") height_unit: String,
        @Field("your_goal") your_goal: String,
        @Field("how_to_achieve_goal") how_to_achieve_goal: String,
        @Field("current_fitness_level") current_fitness_level: String,
        @Field("newsletter") newsletter: String,
        @Field("text_message") text_message: String,
        @Field("phone_status") phone_status: String,
        @Field("currency") currency: String,
        @Field("language") language: String,
        @Field("experience") experience: String,
        @Field("location") location: String,
        @Field("address") address: String,
        @Field("interests") interests: String,
    ):
            Call<SignupResponseModel>

    @Multipart
    @POST("update-profile")
    fun editProfile(
        @Part("user_id") user_id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("username") username: RequestBody,
        @Part("country_code") country_code: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("age") age: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("weight_unit") weight_unit: RequestBody,
        @Part("height") height: RequestBody,
        @Part("height_unit") height_unit: RequestBody,
        @Part("your_goal") your_goal: RequestBody,
        @Part("how_to_achieve_goal") how_to_achieve_goal: RequestBody,
        @Part("current_fitness_level") current_fitness_level: RequestBody,
        @Part("newsletter") newsletter: RequestBody,
        @Part("text_message") text_message: RequestBody,
        @Part("phone_status") phone_status: RequestBody,
        @Part("currency") currency: RequestBody,
        @Part("language") language: RequestBody,
        @Part("experience") experience: RequestBody,
        @Part("location") location: RequestBody,
        @Part("address") address: RequestBody,
        @Part("interests") interests: RequestBody,
        @Part file: MultipartBody.Part?,

        ):
            Call<SignupResponseModel>


    @Headers("Accept: application/json")
    @GET
    fun forgotPassword(@Url url: String): Call<ForgotPasswordResponseModel>


    @Headers("Accept: application/json")
    @POST("reset-password")
    @FormUrlEncoded
    fun resetPassword(
        @Field("email") email: String,
        @Field("password") password: String?,
        @Field("confirm_password") confirm_password: String,
    ):
            Call<SignupResponseModel>

    @Headers("Accept: application/json")
    @POST("change-password")
    @FormUrlEncoded
    fun changePassword(
        @Field("user_id") user_id: String,
        @Field("old_password") old_password: String?,
        @Field("new_password") new_password: String,
    ):
            Call<SignupResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun logout(@Url url: String): Call<BaseResponse>

    @Headers("Accept: application/json")
    @POST("login-with-google")
    @FormUrlEncoded
    fun loginWithGoogle(
        @Field("google_id") google_id: String,
        @Field("username") username: String?,
        @Field("email") email: String,
        @Field("device_type") device_type: String,
        @Field("token") token: String,
        @Field("signup_as") signup_as: String,
    ):
            Call<SignupResponseModel>


    @Headers("Accept: application/json")
    @GET
    fun homeData(@Url url: String): Call<HomeResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun trainerHomeData(@Url url: String): Call<TrainerHomeResponseModel>


    @Headers("Accept: application/json")
    @GET
    fun allClients(@Url url: String): Call<AllClientsResponseModel>



    @Headers("Accept: application/json")
    @POST("get-user-workouts")
    @FormUrlEncoded
    fun getUserWorkouts(
        @Field("trainer_id") trainer_id: String,
        @Field("user_id") user_id: String?,
    ):
            Call<GetUserWorkoutsResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun workoutCategories(@Url url: String): Call<UserCategoryResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun allWorkouts(@Url url: String): Call<AllWorkoutsResponseModel>


    @Headers("Accept: application/json")
    @GET
    fun categoryTrainer(@Url url: String): Call<CategoryTrainerResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun trainerDetail(@Url url: String): Call<TrainerDetailResponseModel>

    @Headers("Accept: application/json")
    @POST("write-review")
    @FormUrlEncoded
    fun writeReviews(
        @Field("user_id") user_id: String,
        @Field("trainer_id") trainer_id: String?,
        @Field("rating_stars") rating_stars: String,
        @Field("review") review: String,
    ):
            Call<BaseResponse>

    @Headers("Accept: application/json")
    @GET
    fun messengerList(@Url url: String): Call<MessengerResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun getMessages(@Url url: String): Call<MessagesResponseModel>

    @Headers("Accept: application/json")
    @GET
    fun messageSeen(@Url url: String): Call<BaseResponse>

    @Headers("Accept: application/json")
    @GET
    fun mealTime(@Url url: String): Call<MealTimesResponseModel>


    @Headers("Accept: application/json")
    @POST("send_message")
    @FormUrlEncoded
    fun sendTextMessage(
        @Field("from_id") from_id: String,
        @Field("to_id") to_id: String?,
        @Field("text") text: String,
        @Field("message_type") message_type: String,
    ):
            Call<BaseResponse>

    @Multipart
    @POST("send_message")
    fun sendImageMessage(
        @Part("from_id") from_id: RequestBody,
        @Part("to_id") to_id: RequestBody,
        @Part("media_type") media_type: RequestBody,
        @Part("text") text: RequestBody,
        @Part("message_type") message_type: RequestBody,
        @Part file: MultipartBody.Part?,

        ):
            Call<SignupResponseModel>

    @Headers("Accept: application/json")
    @POST("user-workout")
    open fun createWorkout(@Body params: JsonObject?): Call<BaseResponse>

    @Headers("Accept: application/json")
    @POST("add-more-workouts")
    open fun addMore(@Body params: JsonObject?): Call<BaseResponse>

    @Headers("Accept: application/json")
    @POST("add-food")
    open fun addFood(@Body params: JsonObject?): Call<BaseResponse>

    @Headers("Accept: application/json")
    @POST("get-user-workouts")
    @FormUrlEncoded
    fun getUserWorkoutDetail(
        @Field("trainer_id") trainer_id: String,
        @Field("user_id") user_id: String
    ):
            Call<GetUserWorkoutsResponseModel>


    @Headers("Accept: application/json")
    @POST("meal-plan")
    @FormUrlEncoded
    fun mealPlan(
        @Field("action") action: String,
        @Field("meal_plan_id") meal_plan_id: String,
        @Field("user_id") user_id: String,
        @Field("trainer_id") trainer_id: String,
        @Field("title") title: String,
        @Field("calorie_target") calorie_target: String,
        @Field("macro_type") macro_type: String,
        @Field("protien") protien: String,
        @Field("carbs") carbs: String,
        @Field("fat") fat: String,
        @Field("fibre") fibre: String,
        @Field("sodium") sodium: String,
        @Field("sugar") sugar: String,
    ):
            Call<CreateMealPlanResponseModel>

    @Headers("Accept: application/json")
    @POST("meal-plan")
    @FormUrlEncoded
    fun viewMealPlan(
        @Field("action") action: String,
        @Field("user_id") user_id: String,
        @Field("trainer_id") trainer_id: String
    ):
            Call<MealPlanResponseModel>

    @Headers("Accept: application/json")
    @POST("search-meals")
    @FormUrlEncoded
    fun searchMeal(
        @Field("search_string") search_string: String,

    ):
            Call<SearchResponseModel>
}