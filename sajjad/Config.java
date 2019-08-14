package com.example.ssoheyli.ticketing_newdesign.Helpfull;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.ssoheyli.ticketing_newdesign.Language.LanguageModel;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s.soheyli on 10/17/2018.
 */

public class Config extends Activity {

    //public static String BaseUrl = "http://dianasolution.com/";
    public static String BaseUrl = "https://portal.zibahome.com/";

    public static String BaseUrl_Alt = "https://tavanasazan.com/";

    public static String LANG_KEY = "langId";

    // LANGUAGE
    public static List<LanguageModel> languageModels = new ArrayList<>();

    public static List<LanguageModel> getLanguageModels() {
        return languageModels;
    }

    public static void setLanguageModels(List<LanguageModel> languageModels) {
        Config.languageModels = languageModels;
    }

    public static int getLCID(Context context) {

        int LCID = 1065;

        if (Config.get_Language(context).equals("english")) {
            for (int i = 0; i < languageModels.size(); i++) {
                if (languageModels.get(i).getCulture().equals("en")) {
                    LCID = languageModels.get(i).getLCID();
                }
            }
            return LCID;
        } else if (Config.get_Language(context).equals("persian")) {
            for (int i = 0; i < languageModels.size(); i++) {
                if (languageModels.get(i).getCulture().equals("fa")) {
                    LCID = languageModels.get(i).getLCID();
                }
            }
            return LCID;
        } else {
            return LCID;
        }
    }

    public static int getLanguageId(Context context) {

        int languageId = 1;

        if (Config.get_Language(context).equals("english")) {
            for (int i = 0; i < languageModels.size(); i++) {
                if (languageModels.get(i).getCulture().equals("en")) {
                    languageId = languageModels.get(i).getLanguageId();
                }
            }
            return languageId;
        } else if (Config.get_Language(context).equals("persian")) {
            for (int i = 0; i < languageModels.size(); i++) {
                if (languageModels.get(i).getCulture().equals("fa")) {
                    languageId = languageModels.get(i).getLanguageId();
                }
            }
            return languageId;
        } else {
            return languageId;
        }
    }

    // LANGUAGE

    public static void SetData(Context context, String user, String pass, int userid) {
        SharedPreferences sh = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("username", user);
        editor.putString("password", pass);
        editor.putInt("userid", userid);
        editor.apply();
    }

    public static void setBaseUrl(Context context, String BaseUrl) {

        SharedPreferences sh = context.getSharedPreferences("URL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("baseurle", BaseUrl);
    }

    public static void Set4DigitPassword(Context context, String password) {
        SharedPreferences pas = context.getSharedPreferences("Password", MODE_PRIVATE);
        SharedPreferences.Editor editor = pas.edit();
        editor.putString("digitpas", password);
        editor.apply();
    }

    public static String get4DigitPassword(Context context) {
        String password = "";
        SharedPreferences pas = context.getSharedPreferences("Password", MODE_PRIVATE);
        SharedPreferences.Editor editor = pas.edit();
        password = pas.getString("digitpas", "");
        return password;
    }

    public static String getusername(Context context) {
        String username = "";
        SharedPreferences sh = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        username = sh.getString("username", "");
        return username;
    }


    public static String getpassword(Context context) {
        String password = "";
        SharedPreferences sh = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        password = sh.getString("password", "");
        return password;
    }


    public static int getuserid(Context context) {
        int userid;
        SharedPreferences sh = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        userid = sh.getInt("userid", 0);
        return userid;
    }


    /*    public static void SetBaseUrl(Context context , String url)
        {
            SharedPreferences sh = context.getSharedPreferences("baseurl", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putString("url", url);
            editor.apply();
        }
    */
    public static String getBaseUrl() {
//        SharedPreferences sh = context.getSharedPreferences("baseurl", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sh.edit();
//        baseurl = sh.getString("url", "");
        return BaseUrl;
    }

    public static void SetToken(Context context, String tok) {
        SharedPreferences sh1 = context.getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sh1.edit();
        editor1.putString("token", tok);
        editor1.apply();
    }

    public static void Set_Storage_Location(Context context, String Document, String Voice, String Picture) {
        SharedPreferences ad = context.getSharedPreferences("address", MODE_PRIVATE);
        SharedPreferences.Editor editor = ad.edit();
        editor.putString("Document", Document);
        editor.putString("Voice", Voice);
        editor.putString("Picture", Picture);
        editor.apply();
    }

    public static String get_Document_Address(Context context) {
        String Address = "";
        SharedPreferences sh = context.getSharedPreferences("address", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        Address = sh.getString("Document", "");
        return Address;
    }

    public static String get_Picture_Address(Context context) {
        String Address = "";
        SharedPreferences sh = context.getSharedPreferences("address", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        Address = sh.getString("Picture", "");
        return Address;
    }


    public static String get_Voice_Address(Context context) {
        String Address = "";
        SharedPreferences sh = context.getSharedPreferences("address", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        Address = sh.getString("Voice", "");
        return Address;
    }


    public static String getToken(Context context) {
        String baseurl = "";
        SharedPreferences sh = context.getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        baseurl = sh.getString("token", "");
        return baseurl;
    }

    public static void Set_Language(Context context, String language) {
        SharedPreferences lan = context.getSharedPreferences("language", MODE_PRIVATE);
        SharedPreferences.Editor editor = lan.edit();
        editor.putString("current_language", language);
        editor.apply();
    }

    public static String get_Language(Context context) {
        String language = "persian";
        SharedPreferences lan = context.getSharedPreferences("language", MODE_PRIVATE);
        SharedPreferences.Editor editor = lan.edit();
        language = lan.getString("current_language", "persian");
        return language;
    }

    public static void set_Color(Context context, int color_primary, int color_Accent, int color_primary_dark, int color_text, int bg_menu_item1, int bg_menu_item2, int bg_menu_item3, int theme, int bg_ripple_key, int bg_key) {
        SharedPreferences color = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = color.edit();
        editor.putInt("color_primary", color_primary);
        editor.putInt("color_Accent", color_Accent);
        editor.putInt("color_primary_dark", color_primary_dark);
        editor.putInt("color_text", color_text);
        editor.putInt("bg_menu_item1", bg_menu_item1);
        editor.putInt("bg_menu_item2", bg_menu_item2);
        editor.putInt("bg_menu_item3", bg_menu_item3);
        editor.putInt("theme", theme);
        editor.putInt("bg_ripple_key", bg_ripple_key);
        editor.putInt("bg_key", bg_key);
        editor.apply();
    }

    public static int get_primary_color(Context context) {
        int primary_color;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        primary_color = sh.getInt("color_primary", R.color.colorPrimary);
        return primary_color;
    }


    public static int get_dark_primary_color(Context context) {
        int dark_primary_color;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        dark_primary_color = sh.getInt("color_primary_dark", R.color.colorPrimaryDark);
        return dark_primary_color;
    }


    public static int get_Accent_color(Context context) {
        int Accent_color;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        Accent_color = sh.getInt("color_Accent", R.color.colorAccent);
        return Accent_color;
    }


    public static int get_text_color(Context context) {
        int text_color;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        text_color = sh.getInt("color_text", R.color.white);
        return text_color;
    }


    public static int get_bg_menu1(Context context) {
        int bg_menu1;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        bg_menu1 = sh.getInt("bg_menu_item1", R.drawable.bg_menu_item1);
        return bg_menu1;
    }


    public static int get_bg_menu2(Context context) {
        int bg_menu2;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        bg_menu2 = sh.getInt("bg_menu_item2", R.drawable.bg_menu_item2);
        return bg_menu2;
    }


    public static int get_bg_menu3(Context context) {
        int bg_menu3;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        bg_menu3 = sh.getInt("bg_menu_item3", R.drawable.bg_menu_item3);
        return bg_menu3;
    }


    public static int get_theme(Context context) {
        int theme;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        theme = sh.getInt("theme", R.style.AppTheme);
        return theme;
    }

    public static int get_ripple_key(Context context) {
        int theme;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        theme = sh.getInt("bg_ripple_key", R.drawable.ripple_keyboard);
        return theme;
    }

    public static int get_key(Context context) {
        int theme;
        SharedPreferences sh = context.getSharedPreferences("Color", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        theme = sh.getInt("bg_key", R.drawable.material_keyboard);
        return theme;
    }


}
