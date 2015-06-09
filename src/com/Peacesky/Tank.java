package com.Peacesky;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Tank {
    public static final int XSPEED = 8;
    public static final int YSPEED = 8;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private static Random r = new Random();
    ;
    int x, y;
    TankClient tc = null;
    private int oldX, oldY;
    private int life = 100;
    private int step = r.nextInt(12) + 3;
    private boolean good;
    private boolean live = true;
    private boolean bL = false, bU = false, bR = false, bD = false;
    private Direction dir = Direction.STOP;
    ;
    private Direction ptDir = Direction.D;
    private BloodBar bb = new BloodBar();

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] tankImage = null;
    private static Map<String, Image> imgs = new HashMap<>();

    // 静态代码区
    // 适合执行变量初始化
    static {
        tankImage = new Image[] {
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankL.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankLU.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankU.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankRU.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankR.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankRD.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankD.gif")),
                tk.getImage(Explode.class.getClassLoader().getResource("image/tankLD.gif"))
        };

        // static 代码区更灵活 由static代码区才能允许添加以下这段代码
        // 形成键值对 使用命名代替原来的下标标示  更易于维护
        imgs.put("L", tankImage[0]);
        imgs.put("LU", tankImage[1]);
        imgs.put("U", tankImage[2]);
        imgs.put("RU", tankImage[3]);
        imgs.put("R", tankImage[4]);
        imgs.put("RD", tankImage[5]);
        imgs.put("D", tankImage[6]);
        imgs.put("LD", tankImage[7]);
    }

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.setGood(good);
    }

    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.dir = dir;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (good && this.live) bb.draw(g);
        if (!live) {
            if (!isGood()) {
                tc.tanks.remove(this);
            }
            return;
        }

        switch (ptDir) {
            case L:
                g.drawImage(imgs.get("L"), x, y, null);
                break;
            case LU:
                g.drawImage(imgs.get("LU"), x, y, null);
                break;
            case U:
                g.drawImage(imgs.get("U"), x, y, null);
                break;
            case RU:
                g.drawImage(imgs.get("RU"), x, y, null);
                break;
            case R:
                g.drawImage(imgs.get("R"), x, y, null);
                break;
            case RD:
                g.drawImage(imgs.get("RD"), x, y, null);
                break;
            case D:
                g.drawImage(imgs.get("D"), x, y, null);
                break;
            case LD:
                g.drawImage(imgs.get("LD"), x, y, null);
                break;
        }
        move();
    }

    public void move() {
        this.oldX = x;
        this.oldY = y;
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }

        if (x < 0) x = 0;    //左不出界
        //if(x < 30) x = 30;
        if (y < 30) y = 30;  //上不出界
        //if(y < 0) y = 0;
        if (x + Tank.WIDTH > TankClient.Game_wigth) x = TankClient.Game_wigth - Tank.WIDTH;
        if (y + Tank.HEIGHT > TankClient.Game_height) y = TankClient.Game_height - Tank.HEIGHT;

        //tcDir(); 注释掉后 敌方坦克能够动起来了
        //ms.move();

        if (!isGood()) {
            Direction[] dirs = Direction.values();
            if (step == 0) {
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                dir = dirs[rn];
                if (dir != Direction.STOP) {
                    ptDir = dir;
                }
            }
            step--;
            if (r.nextInt(40) > 38) {
                this.fire();
            }
        }
    }

    public void tcDir() {
        if (bL && !bU && !bR && !bD) dir = Direction.L;
        else if (bL && bU && !bR && !bD) dir = Direction.LU;
        else if (!bL && bU && !bR && !bD) dir = Direction.U;
        else if (!bL && bU && bR && !bD) dir = Direction.RU;
        else if (!bL && !bU && bR && !bD) dir = Direction.R;
        else if (!bL && !bU && bR && bD) dir = Direction.RD;
        else if (!bL && !bU && !bR && bD) dir = Direction.D;
        else if (bL && !bU && !bR && bD) dir = Direction.LD;
        else if (!bL && !bU && !bR && !bD) dir = Direction.STOP;
        if (dir != Direction.STOP) {
            ptDir = dir;
        }
//move();

    }

    public void keyPress(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_R:
                if (!this.live) {
                    this.live = true;
                    this.life = 100;
                }
                break;
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        tcDir();    //修改处，修改后，敌方坦克到达底部后，我方坦克也不会动弹不得；
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A:
                if (this.live) {
                    superFire();
                    break;
                }
            case KeyEvent.VK_CONTROL:
                fire();
                break;    //少写break的话容易造成穿透；
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
//tcDir();
    }

    public Missile fire() {
        if (!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile s = new Missile(x, y, isGood(), ptDir, tc);
        tc.msList.add(s);
        return s;
    }

    public Missile fire(Direction dirs) {
        if (!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile s = new Missile(x, y, isGood(), dirs, tc);
        tc.msList.add(s);
        return s;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public void stay() {
        this.x = oldX;
        this.y = oldY;
    }

    public boolean hitWall(Wall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            //this.dir = com.Peacesky.Direction.STOP;
            this.stay();
            return true;
        }
        return false;
    }

    public boolean hitEach(java.util.List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (this != t) {
                if (this.live && t.live && this.getRect().intersects(t.getRect())) {
                    //this.dir = com.Peacesky.Direction.STOP;
                    this.stay();
                    t.stay();
                    return true;
                }
            }
        }
        return false;
    }

    private void superFire() {
        Direction[] dirs = Direction.values();
        for (int i = 0; i < 8; i++) {
            tc.msList.add(fire(dirs[i]));
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean eat(Blood b) {
        if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
            life = 100;
            b.setLive(false);
            return true;
        }
        return false;
    }

    // enum com.Peacesky.Direction {L, LU, U, RU, R, RD, D, LD, STOP}	独立成类

    public class BloodBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.GRAY);
            g.drawRect(x, y - 20, WIDTH, 10);
            int w = WIDTH * life / 100;
            g.fillRect(x, y - 20, w, 10);
            g.setColor(c);
        }
    }
}
