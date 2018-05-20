# SampleLibrary
Android Sample Library

1 Internet
  - AndroidManifest.xml 에 <uses-permission android:name="android.permission.INTERNET" /> 추가

2. Glide 
  - gradle(Project) : repositories{...} 에 mavenCentral() 추가
  - gradle(App) : dependencies{...}에 implementation 'com.github.bumptech.glide:glide:3.8.0' 추가
  - Applicatoin 클래스에 Glide OOM과 관련된 메소드 2개 오버라이딩(onLowMemory(), onTrimMemory())
  ※ Glide의 경우 v3, v4 2가지 버전이 있는데 OkHttp3과 함께 사용하는 경우 v3를 권장합니다.
     따라서, 이번 예제의 경우 OkHttp3를 함께 사용하기 때문에 v3로 버전을 맞추었습니다.

3. CircleImageView
  - gradle(App) :dependencies{...}에 implementation 'de.hdodenhof:circleimageview:2.2.0' 추가

4. ButterKnife
  - gradle(App) :dependencies{...}에 implementation 'com.jakewharton:butterknife:8.8.1' 추가
  - gradle(App) :dependencies{...}에 annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1' 추가
  ※ Activity에서 사용하는 경우
     - onCreate() -> setContentView() 호출한 이후에 ButterKnife.bind(this); 추가
  ※ Fragment에서 사용하는 경우
     - onCreateView() -> 레이아웃 inflate 이후에 ButterKnife.bind(this, rootView); 호출
  ※ ViewHolder에서 사용하는 경우
     - ViewHolder의 생성자에서 ButterKnife.bind(this, view); 호출

5. OkHttp3
  - gradle(App) :dependencies{...}에 implementation 'com.squareup.okhttp3:okhttp:3.10.0' 추가
  - Singleton 방식으로 구현

6. GSON
  - gradle(App) :dependencies{...}에 implementation 'com.google.code.gson:gson:2.8.4' 추가
  - GSON의 대상이되는 데이터 클래스는 @SerializedName, Parcelable를 사용하여 구현한다.
  
7. API 
  - Glide : https://github.com/bumptech/glide/tree/3.0
  - CircleImageView : https://github.com/hdodenhof/CircleImageView
  - ButterKnife : http://jakewharton.github.io/butterknife/
  - OkHttp3 : http://square.github.io/okhttp/
  - Gson : https://github.com/google/gson
