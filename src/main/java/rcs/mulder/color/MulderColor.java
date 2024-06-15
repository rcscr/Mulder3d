package rcs.mulder.color;

import rcs.mulder.math.MathUtils;
import rcs.mulder.utils.XORShift;

public final class MulderColor {
  
  public static final MulderColor snow                 = new MulderColor(255, 250, 250);
  public static final MulderColor snow2                = new MulderColor(238, 233, 233);
  public static final MulderColor snow3                = new MulderColor(205, 201, 201);
  public static final MulderColor snow4                = new MulderColor(139, 137, 137);
  public static final MulderColor ghostWhite           = new MulderColor(248, 248, 255);
  public static final MulderColor whiteSmoke           = new MulderColor(245, 245, 245);
  public static final MulderColor gainsboro            = new MulderColor(220, 220, 220);
  public static final MulderColor floralWhite          = new MulderColor(255, 250, 240);
  public static final MulderColor oldLace              = new MulderColor(253, 245, 230);
  public static final MulderColor linen                = new MulderColor(240, 240, 230);
  public static final MulderColor antiqueWhite         = new MulderColor(250, 235, 215);
  public static final MulderColor antiqueWhite2        = new MulderColor(238, 223, 204);
  public static final MulderColor antiqueWhite3        = new MulderColor(205, 192, 176);
  public static final MulderColor antiqueWhite4        = new MulderColor(139, 131, 120);
  public static final MulderColor papayaWhip           = new MulderColor(255, 239, 213);
  public static final MulderColor blanchedAlmond       = new MulderColor(255, 235, 205);
  public static final MulderColor bisque               = new MulderColor(255, 228, 196);
  public static final MulderColor bisque2              = new MulderColor(238, 213, 183);
  public static final MulderColor bisque3              = new MulderColor(205, 183, 158);
  public static final MulderColor bisque4              = new MulderColor(139, 125, 107);
  public static final MulderColor peachPuff            = new MulderColor(255, 218, 185);
  public static final MulderColor peachPuff2           = new MulderColor(238, 203, 173);
  public static final MulderColor peachPuff3           = new MulderColor(205, 175, 149);
  public static final MulderColor peachPuff4           = new MulderColor(139, 119, 101);
  public static final MulderColor navajoWhite          = new MulderColor(255, 222, 173);
  public static final MulderColor moccasin             = new MulderColor(255, 228, 181);
  public static final MulderColor cornsilk             = new MulderColor(255, 248, 220);
  public static final MulderColor cornsilk2            = new MulderColor(238, 232, 205);
  public static final MulderColor cornsilk3            = new MulderColor(205, 200, 177);
  public static final MulderColor cornsilk4            = new MulderColor(139, 136, 120);
  public static final MulderColor ivory                = new MulderColor(255, 255, 240);
  public static final MulderColor ivory2               = new MulderColor(238, 238, 224);
  public static final MulderColor ivory3               = new MulderColor(205, 205, 193);
  public static final MulderColor ivory4               = new MulderColor(139, 139, 131);
  public static final MulderColor lemonChiffon         = new MulderColor(255, 250, 205);
  public static final MulderColor seashell             = new MulderColor(255, 245, 238);
  public static final MulderColor seashell2            = new MulderColor(238, 229, 222);
  public static final MulderColor seashell3            = new MulderColor(205, 197, 191);
  public static final MulderColor seashell4            = new MulderColor(139, 134, 130);
  public static final MulderColor honeydew             = new MulderColor(240, 255, 240);
  public static final MulderColor honeydew2            = new MulderColor(244, 238, 224);
  public static final MulderColor honeydew3            = new MulderColor(193, 205, 193);
  public static final MulderColor honeydew4            = new MulderColor(131, 139, 131);
  public static final MulderColor mintCream            = new MulderColor(245, 255, 250);
  public static final MulderColor azure                = new MulderColor(240, 255, 255);
  public static final MulderColor aliceBlue            = new MulderColor(240, 248, 255);
  public static final MulderColor lavender             = new MulderColor(230, 230, 250);
  public static final MulderColor lavenderBlush        = new MulderColor(255, 240, 245);
  public static final MulderColor mistyRose            = new MulderColor(255, 228, 225);
  public static final MulderColor white                = new MulderColor(255, 255, 255);
  public static final MulderColor black                = new MulderColor(  0,   0,   0);
  public static final MulderColor darkGray             = new MulderColor( 50,  50,  50);
  public static final MulderColor veryDarkGray         = new MulderColor( 20,  20,  20);
  public static final MulderColor superDarkGray        = new MulderColor( 10,  10,  10);
  public static final MulderColor darkSlateGray        = new MulderColor( 49,  79,  79);
  public static final MulderColor dimGray              = new MulderColor(105, 105, 105);
  public static final MulderColor slateGray            = new MulderColor(112, 138, 144);
  public static final MulderColor lightSlateGray       = new MulderColor(119, 136, 153);
  public static final MulderColor gray                 = new MulderColor(190, 190, 190);
  public static final MulderColor lightGray            = new MulderColor(211, 211, 211);
  public static final MulderColor midnightBlue         = new MulderColor( 25,  25, 112);
  public static final MulderColor navy                 = new MulderColor(  0,   0, 128);
  public static final MulderColor cornflowerBlue       = new MulderColor(100, 149, 237);
  public static final MulderColor darkSlateBlue        = new MulderColor( 72,  61, 139);
  public static final MulderColor slateBlue            = new MulderColor(106,  90, 205);
  public static final MulderColor mediumSlateBlue      = new MulderColor(123, 104, 238);
  public static final MulderColor lightSlateBlue       = new MulderColor(132, 112, 255);
  public static final MulderColor mediumBlue           = new MulderColor(  0,   0, 205);
  public static final MulderColor royalBlue            = new MulderColor( 65, 105, 225);
  public static final MulderColor blue                 = new MulderColor(  0,   0, 255);
  public static final MulderColor dodgerBlue           = new MulderColor( 30, 144, 255);
  public static final MulderColor deepSkyBlue          = new MulderColor(  0, 191, 255);
  public static final MulderColor skyBlue              = new MulderColor(135, 206, 250);
  public static final MulderColor lightSkyBlue         = new MulderColor(135, 206, 250);
  public static final MulderColor steelBlue            = new MulderColor( 70, 130, 180);
  public static final MulderColor lightSteelBlue       = new MulderColor(176, 196, 222);
  public static final MulderColor lightBlue            = new MulderColor(173, 216, 230);
  public static final MulderColor powderBlue           = new MulderColor(176, 224, 230);
  public static final MulderColor paleTurquoise        = new MulderColor(175, 238, 238);
  public static final MulderColor darkTurquoise        = new MulderColor(  0, 206, 209);
  public static final MulderColor mediumTurquoise      = new MulderColor( 72, 209, 204);
  public static final MulderColor turquoise            = new MulderColor( 64, 224, 208);
  public static final MulderColor cyan                 = new MulderColor(  0, 255, 255);
  public static final MulderColor lightCyan            = new MulderColor(224, 255, 255);
  public static final MulderColor cadetBlue            = new MulderColor( 95, 158, 160);
  public static final MulderColor mediumAquamarine     = new MulderColor(102, 205, 170);
  public static final MulderColor aquamarine           = new MulderColor(127, 255, 212);
  public static final MulderColor green                = new MulderColor(  0, 255,   0);
  public static final MulderColor darkGreen            = new MulderColor(  0, 100,   0);
  public static final MulderColor darkOliveGreen       = new MulderColor( 85, 107,  47);
  public static final MulderColor darkSeaGreen         = new MulderColor(143, 188, 143);
  public static final MulderColor seaGreen             = new MulderColor( 46, 139,  87);
  public static final MulderColor mediumSeaGreen       = new MulderColor( 60, 179, 113);
  public static final MulderColor lightSeaGreen        = new MulderColor( 32, 178, 170);
  public static final MulderColor paleGreen            = new MulderColor(152, 251, 152);
  public static final MulderColor springGreen          = new MulderColor(  0, 255, 127);
  public static final MulderColor lawnGreen            = new MulderColor(124, 252,   0);
  public static final MulderColor chartreuse           = new MulderColor(127, 255,   0);
  public static final MulderColor mediumSpringGreen    = new MulderColor(  0, 250, 154);
  public static final MulderColor greenYellow          = new MulderColor(173, 255,  47);
  public static final MulderColor limeGreen            = new MulderColor( 50, 205,  50);
  public static final MulderColor yellowGreen          = new MulderColor(154, 205,  50);
  public static final MulderColor forestGreen          = new MulderColor( 34, 139,  34);
  public static final MulderColor oliveDrab            = new MulderColor(107, 142,  35);
  public static final MulderColor darkKhaki            = new MulderColor(189, 183, 107);
  public static final MulderColor khaki                = new MulderColor(240, 230, 140);
  public static final MulderColor paleGoldenrod        = new MulderColor(238, 232, 170);
  public static final MulderColor lightGoldenrodYellow = new MulderColor(250, 250, 210);
  public static final MulderColor lightYellow          = new MulderColor(255, 255, 224);
  public static final MulderColor yellow               = new MulderColor(255, 255,   0);
  public static final MulderColor gold                 = new MulderColor(255, 215,   0);
  public static final MulderColor lightGoldenrod       = new MulderColor(238, 221, 130);
  public static final MulderColor goldenrod            = new MulderColor(218, 165,  32);
  public static final MulderColor darkGoldenrod        = new MulderColor(184, 134,  11);
  public static final MulderColor rosyBrown            = new MulderColor(188, 143, 143);
  public static final MulderColor indianRed            = new MulderColor(205,  92,  92);
  public static final MulderColor saddleBrown          = new MulderColor(139,  69,  19);
  public static final MulderColor sienna               = new MulderColor(160,  82,  45);
  public static final MulderColor peru                 = new MulderColor(205, 133,  63);
  public static final MulderColor burlywood            = new MulderColor(222, 184, 135);
  public static final MulderColor beige                = new MulderColor(245, 245, 220);
  public static final MulderColor wheat                = new MulderColor(245, 222, 179);
  public static final MulderColor sandyBrown           = new MulderColor(244, 164,  96);
  public static final MulderColor tan                  = new MulderColor(210, 180, 140);
  public static final MulderColor chocolate            = new MulderColor(210, 105,  30);
  public static final MulderColor firebrick            = new MulderColor(178,  34,  34);
  public static final MulderColor brown                = new MulderColor(165,  42,  42);
  public static final MulderColor darkSalmon           = new MulderColor(233, 150, 122);
  public static final MulderColor salmon               = new MulderColor(250, 128, 114);
  public static final MulderColor lightSalmon          = new MulderColor(255, 160, 122);
  public static final MulderColor orange               = new MulderColor(255, 165,   0);
  public static final MulderColor darkOrange           = new MulderColor(255, 140,   0);
  public static final MulderColor coral                = new MulderColor(255, 127,  80);
  public static final MulderColor lightCoral           = new MulderColor(240, 128, 128);
  public static final MulderColor tomato               = new MulderColor(255,  99,  71);
  public static final MulderColor orangeRed            = new MulderColor(255,  69,   0);
  public static final MulderColor red                  = new MulderColor(255,   0,   0);
  public static final MulderColor hotPink              = new MulderColor(255, 105, 180);
  public static final MulderColor deepPink             = new MulderColor(255,  20, 147);
  public static final MulderColor pink                 = new MulderColor(255, 192, 203);
  public static final MulderColor lightPink            = new MulderColor(255, 182, 193);
  public static final MulderColor paleVioletRed        = new MulderColor(219, 112, 147);
  public static final MulderColor maroon               = new MulderColor(176,  48,  96);
  public static final MulderColor mediumVioletRed      = new MulderColor(199,  21, 133);
  public static final MulderColor violetRed            = new MulderColor(208,  32, 144);
  public static final MulderColor violet               = new MulderColor(238, 130, 238);
  public static final MulderColor plum                 = new MulderColor(221, 160, 221);
  public static final MulderColor orchid               = new MulderColor(218, 112, 214);
  public static final MulderColor mediumOrchid         = new MulderColor(186,  85, 211);
  public static final MulderColor darkOrchid           = new MulderColor(153,  50, 204);
  public static final MulderColor darkViolet           = new MulderColor(148,   0, 211);
  public static final MulderColor blueViolet           = new MulderColor(138,  43, 226);
  public static final MulderColor purple               = new MulderColor(160,  32, 240);
  public static final MulderColor mediumPurple         = new MulderColor(147, 112, 219);
  public static final MulderColor thistle              = new MulderColor(216, 191, 216);
  
  private static final double DARKENING_FACTOR = 0.7;
  
  private int r;
  private int g;
  private int b;
  private int a;

  private int rgba;

  public MulderColor(MulderColor color) {
    this(color.r, color.g, color.b, color.a);
  }

  public MulderColor(MulderColor color, int alpha) {
    this(color.r, color.g, color.b, alpha);
  }

  public MulderColor(int rgba) {
    this(ColorUtils.getRedFromRGBA(rgba), 
         ColorUtils.getGreenFromRGBA(rgba),  
         ColorUtils.getBlueFromRGBA(rgba),
         ColorUtils.getAlphaFromRGBA(rgba));
  }

  public MulderColor(int r, int g, int b) {
    this(r, g, b, 255);
  }

  public MulderColor(int r, int g, int b, int a) {
    this.r = Math.min(Math.max(r, 0), 255);
    this.g = Math.min(Math.max(g, 0), 255);
    this.b = Math.min(Math.max(b, 0), 255);
    this.a = Math.min(Math.max(a, 0), 255);
    
    this.rgba = ColorUtils.getRGBA(this.r, this.g, this.b, this.a);
  }

  public int getRed() {
    return r;
  }

  public int getGreen() {
    return g;
  }

  public int getBlue() {
    return b;
  }

  public int getAlpha() {
    return a;
  }

  public int getRGBA() {
    return rgba;
  }

  public boolean isTransparent() {
    return a < 255;
  }

  public MulderColor add(MulderColor that) {
    int R = this.r + that.r;
    int G = this.g + that.g;
    int B = this.b + that.b;
    int A = this.a;
    
    return new MulderColor(R, G, B, A);
  }

  public MulderColor sub(MulderColor that) {
    int R = this.r - that.r;
    int G = this.g - that.g;
    int B = this.b - that.b;
    int A = this.a;
    
    return new MulderColor(R, G, B, A);
  }

  public MulderColor mul(double factor) {
    return new MulderColor(MathUtils.roundToInt(r * factor),
                         MathUtils.roundToInt(g * factor), 
                         MathUtils.roundToInt(b * factor), a);
  }

  public double luminance() {
    return 0.2125*r + 0.7154*g + 0.0721*b;
  }

  public MulderColor brighter() {
    int R = r;
    int G = g;
    int B = b;
    int A = a;

    int i = (int)(1.0 / (1.0-DARKENING_FACTOR));
    if (R == 0 && G == 0 && B == 0) {
        return new MulderColor(i, i, i, A);
    }
    if (R > 0 && R < i) R = i;
    if (G > 0 && G < i) G = i;
    if (B > 0 && B < i) B = i;

    return new MulderColor(Math.min((int)(R / DARKENING_FACTOR), 255),
                         Math.min((int)(G / DARKENING_FACTOR), 255),
                         Math.min((int)(B / DARKENING_FACTOR), 255), A);
  }

  public MulderColor darker() {
      return mul(DARKENING_FACTOR);
  }

  public MulderColor fadeTo(double d) {
    return new MulderColor(r, g, b, (int) (a * d));
  }

  public MulderColor opaque() {
    return new MulderColor(r, g, b);
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + r;
    result = 31 * result + g;
    result = 31 * result + b;
    result = 31 * result + a;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MulderColor)) {
      return false;
    }
    
    var that = (MulderColor) obj;

    return this.r == that.r 
        && this.g == that.g
        && this.b == that.b
        && this.a == that.a;
  }

  @Override
  public String toString() {
    return String.format("%s:[r=%s,g=%s,b=%s,a=%s]", super.toString(), this.r, this.g, this.b, this.a);
  }

  public static MulderColor randomColor() {
    return new MulderColor(XORShift.getInstance().randomInt(256),
                         XORShift.getInstance().randomInt(256), 
                         XORShift.getInstance().randomInt(256));
  }
}