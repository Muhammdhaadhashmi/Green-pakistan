package com.example.greenplants_pak;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .enableDarkMode(false)
                .setImage(R.drawable.logo)
                .addItem(new Element().setTitle("Version 6.2"))
                .addItem(adsElement)
                .setDescription("Save you valuable time and energy by taking away the hassle of home cooking! Imagine a tasty, authentic homemade meal without going to the store, prepping ingredients, laboring in front of the" +
                        " stove or dealing with your staff at home, delivered hassle-free to your doorstep")
                .addGroup("Connect with us")
                .addEmail("muqadusumar@gmail.com")
                .addWebsite("http://admissions.comsats.edu.pk/Home/Login")
                .addFacebook("facebook.com")
                .addTwitter("comstas")
                .addYoutube("KpTTuaqypdU")
                .addPlayStore("com.cosmats.pro")
                .addInstagram("medyo80")
                .addGitHub("medyo")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format("copy rihgt", Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.icon_copu);
        copyRightsElement.setAutoApplyIconTint(true);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUs.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }
}