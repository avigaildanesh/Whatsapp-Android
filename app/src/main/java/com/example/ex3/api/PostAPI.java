package com.example.ex3.api;

//public class PostAPI {
//    private MutableLiveData<List<Post>> postListData;
//    private PostDao dao;
//    private Retrofit retrofit;
//    private WebServiceAPI webServiceAPI;
//
//    public PostAPI(MutableLiveData<List<Post>> postListData, PostDao dao) {
//        this.postListData = postListData;
//        this.dao = dao;
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        webServiceAPI = retrofit.create(WebServiceAPI.class);
//    }
//
//    public void get() {
//        Call<List<Post>> call = webServiceAPI.getPosts();
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                new Thread(() -> {
//                    dao.clear();
//                    dao.insertList(response.body());
//                    postListData.postValue(dao.get());
//                }).start();
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                // Handle failure
//            }
//        });
//    }
//}
