package czerwone.krokodyle.czerwone_krokodyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.czerwone_krokodyle.R;

public class Loading_Screen extends AppCompatActivity {

    private RelativeLayout bgimg;
    private ImageView ckrkdl;
    Handler handler = new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        bgimg = findViewById(R.id.MAIN_LOADING_BG);
        ckrkdl = findViewById(R.id.krokodyl_loading);

        try{
            YoYo.with(Techniques.Shake).duration(1000).repeat(2).playOn(ckrkdl);
            new Handler(getMainLooper()).postDelayed(() -> {
                bgimg.setBackground(getResources().getDrawable(R.drawable.cktlo_loading2));
                //YoYo.with(Techniques.Flash).duration(200).playOn(bgimg);
                new Handler(getMainLooper()).postDelayed(() -> {
                    //YoYo.with(Techniques).duration(200).playOn(bgimg);
                    bgimg.setBackground(getResources().getDrawable(R.drawable.cktlo_loading3));
                    new Handler(getMainLooper()).postDelayed(() -> {
                        bgimg.setBackground(getResources().getDrawable(R.drawable.cktlo));
                        new Handler(getMainLooper()).postDelayed(() -> {
                            startActivity(new Intent(Loading_Screen.this,MainActivity.class));
                            finish();
                            handler.removeCallbacks(runnable);
                        }, 500);
                    }, 500);
                }, 500);
            }, 500);


        } catch (Exception e){
            e.printStackTrace();
        }

    }
    int i=0;
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, 400);
                try{
                    TextView loadingtext = findViewById(R.id.text_loading);
                            String dots="";
                            for(int j = i%3; j>=0 %3; j--){
                                dots+=".";
                            }
                            String finalDots = dots;
                            loadingtext.setText(("Loading"+ finalDots));

                        i++;
                }catch (Exception ee){

                }
            }
        }, 400);
    }
    public void KrokodylKlikaj(View v){
        //musiałem to dodać bo crashuje w tym widoku
    }
}
