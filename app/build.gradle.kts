plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-parcelize")
	id("kotlin-kapt")
}

android {
	namespace = "com.dicodingsubmit.githubuser"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.dicodingsubmit.githubuser"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.11.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

	// fragment
	implementation("androidx.fragment:fragment-ktx:1.6.2")

	// glide: display online image
	implementation("com.github.bumptech.glide:glide:4.16.0")

	// circleimageview: display circle image
	implementation("de.hdodenhof:circleimageview:3.1.0")

	// lottie files
	implementation("com.airbnb.android:lottie:6.3.0")

	//retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

	//room
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
	implementation("androidx.room:room-runtime:2.6.1")
	kapt("androidx.room:room-compiler:2.6.1")

	// data store
	implementation("androidx.datastore:datastore-preferences:1.0.0")

}