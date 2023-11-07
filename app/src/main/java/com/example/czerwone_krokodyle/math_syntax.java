package com.example.czerwone_krokodyle;

import android.graphics.drawable.Drawable;

import ru.noties.jlatexmath.JLatexMathDrawable;

public class math_syntax {
    public JLatexMathDrawable set_Math(String latex){
        final JLatexMathDrawable drawable = JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(0x00ffffff)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
        return drawable;
    }
    public JLatexMathDrawable set_Fancy_Math(String latex,int bg){
        final JLatexMathDrawable drawable = JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(bg)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
        return drawable;
    }
    public JLatexMathDrawable set_Very_Fancy_Math(String latex,int bg,int clr){
        final JLatexMathDrawable drawable = JLatexMathDrawable.builder(latex)
                .textSize(70)
                .padding(8)
                .background(bg)
                .color(clr)
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build();
        return drawable;
    }

}
