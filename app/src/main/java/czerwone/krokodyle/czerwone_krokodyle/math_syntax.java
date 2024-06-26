package czerwone.krokodyle.czerwone_krokodyle;

import android.graphics.drawable.Drawable;

import ru.noties.jlatexmath.JLatexMathDrawable;

public class math_syntax {
    public JLatexMathDrawable set_Math(String latex){
        return JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(0x00ffffff)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
    }
    public JLatexMathDrawable set_Fancy_Math(String latex,int bg){
        return JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(bg)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
    }
    public JLatexMathDrawable set_Very_Fancy_Math(String latex,int bg,int clr){
        return JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(bg)
                .color(clr)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
    }
    public JLatexMathDrawable set_Very_Fancy_Math(String latex, Drawable bg, int clr){
        return JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(bg)
                .color(clr)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
    }
    public JLatexMathDrawable set_Math(String latex,int align){
        return JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .align(align)
                .build();
    }

}
