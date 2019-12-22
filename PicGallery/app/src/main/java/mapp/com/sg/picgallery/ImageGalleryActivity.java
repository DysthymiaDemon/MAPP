package mapp.com.sg.picgallery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class ImageGalleryActivity extends AppCompatActivity {
    private ImageSwitcher sw;
    private Button moveleft, moveright;

    // Place images into an array
    int imgIds[] ={
            R.drawable.pic1,
            R.drawable.loginhere,
            R.drawable.mylocation
    };
    int imgCount = imgIds.length;
    int currentIndex = -1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moveleft = (Button) findViewById(R.id.btnLeft);
        moveright = (Button) findViewById(R.id.btnRight);
        moveleft.setEnabled(false) ;

        sw = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        sw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        moveleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                if (currentIndex == 0){
                    // Disable moveleft button when it is first pictures
                    moveleft.setEnabled(false) ;
                } else{
                    moveright.setEnabled(true) ;
                }

                sw.setImageResource(imgIds[currentIndex]);
                Toast.makeText(getApplicationContext(), "Previous Image " + currentIndex,
                        Toast.LENGTH_LONG).show();

            }
        });

        moveright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex == 0){
                    // Disable right button when it reach last image
                    moveleft.setEnabled(false) ;
                    moveright.setEnabled(true) ;
                } else{
                    if (currentIndex == imgCount - 1){
                        moveright.setEnabled(false) ;
                    } else {
                        moveleft.setEnabled(true) ;
                    }
                }
                Toast.makeText(getApplicationContext(), "Next Image " + currentIndex,
                        Toast.LENGTH_LONG).show();
                sw.setImageResource(imgIds[currentIndex]);

            }
        });


    }
}
