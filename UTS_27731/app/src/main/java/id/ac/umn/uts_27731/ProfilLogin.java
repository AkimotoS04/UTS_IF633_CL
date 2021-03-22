package id.ac.umn.uts_27731;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProfilLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_login);

        TextView link_option = findViewById(R.id.link_option);
        TextView link_clickable = findViewById(R.id.link_clickable);
        TextView link_tutorials = findViewById(R.id.link_tutorial);
        TextView link_music = findViewById(R.id.linkmusic);

        link_option.setMovementMethod(LinkMovementMethod.getInstance());
        link_clickable.setMovementMethod(LinkMovementMethod.getInstance());
        link_tutorials.setMovementMethod(LinkMovementMethod.getInstance());
        link_clickable.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        Intent backk = new Intent(ProfilLogin.this, DaftarLagu.class);
        startActivity(backk);
        //super.onBackPressed();
    }

}