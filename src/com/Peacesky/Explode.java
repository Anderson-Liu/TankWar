package com.Peacesky;

import java.awt.*;
import java.net.URL;


public class Explode {
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] imgs = {
            tk.getImage(Explode.class.getClassLoader().getResource("image/0.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/1.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/2.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/3.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/4.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/5.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/6.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/7.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/8.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/9.gif")),
            tk.getImage(Explode.class.getClassLoader().getResource("image/10.gif"))
    };

    URL url1 = Explode.class.getClassLoader().getResource("/image/10.gif");
    URL url2 = Explode.class.getClassLoader().getResource("image/10.gif");
    URL url3 = Explode.class.getClassLoader().getResource("Image/10.gif");
    URL url4 = Explode.class.getClassLoader().getResource("/Image/10.gif");

    Image testImage = tk.getImage("Image/10.gif");
    int x, y;
    // The diameter of explode
    // int[] diameter = {4, 7, 12, 18, 26, 32, 49, 52, 47, 30, 14, 6};g
    int step = 0;
    private boolean live = true;
    private TankClient tc;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }


    public void draw(Graphics g) {
        if (!live)
            return;
        if (step == imgs.length) {
            live = false;
            step = 0;
            return;
        }

        g.drawImage(imgs[step], x, y, null);

        step++;
    }


    public boolean isLive() {
        // TODO Auto-generated method stub
        return live;
    }

}
