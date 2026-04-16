package com.example.a207408_cikguizwan_lab02.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.example.a207408_cikguizwan_lab02.R

// 设置 Google Fonts
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// 定义字体
val MulishFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Mulish"), fontProvider = provider)
)

val UbuntuFontFamily = FontFamily(
    Font(googleFont = GoogleFont("Ubuntu"), fontProvider = provider)
)

// 将字体分配给角色
val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = MulishFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),


    bodyLarge = TextStyle(
        fontFamily = UbuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = UbuntuFontFamily,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = UbuntuFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)