package rcs.mulder.color;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ColorUtilsTest {
  
  @Test
  void testIsTransparent() {
    // Arrange
    MulderColor is = new MulderColor(1, 2, 3, 100);
    MulderColor isNot = new MulderColor(1, 2, 3, 255);
    
    // Act
    boolean isResult = ColorUtils.isTransparent(is.getRGBA());
    boolean isNotResult = ColorUtils.isTransparent(isNot.getRGBA());
    
    // Assert
    assertThat(isResult).isTrue();
    assertThat(isNotResult).isFalse();
  }
  
  @Test
  void testGetRedFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3);
    
    // Act
    int red = ColorUtils.getRedFromRGBA(color.getRGBA());
    
    // Assert    
    assertThat(red).isEqualTo(color.getRed());
  }
  
  @Test
  void testGetGreenFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3);
    
    // Act
    int green = ColorUtils.getGreenFromRGBA(color.getRGBA());
    
    // Assert    
    assertThat(green).isEqualTo(color.getGreen());
  }
  
  @Test
  void testGetBlueFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3);
    
    // Act
    int blue = ColorUtils.getBlueFromRGBA(color.getRGBA());
    
    // Assert    
    assertThat(blue).isEqualTo(color.getBlue());
  }
  
  @Test
  void testGetAlphaFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3, 4);
    
    // Act
    int alpha = ColorUtils.getAlphaFromRGBA(color.getRGBA());
    
    // Assert    
    assertThat(alpha).isEqualTo(color.getAlpha());
  }
  
  @Test
  void testSetRedToRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3, 4);
    
    // Act
    int withRed = ColorUtils.setRedToRGBA(color.getRGBA(), 0);
    
    // Assert    
    assertThat(ColorUtils.getRedFromRGBA(withRed)).isEqualTo(0);
  }
  
  @Test
  void testSetGreenFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3, 4);
    
    // Act
    int withGreen = ColorUtils.setGreenToRGBA(color.getRGBA(), 0);
    
    // Assert    
    assertThat(ColorUtils.getGreenFromRGBA(withGreen)).isEqualTo(0);
  }
  
  @Test
  void testSetBlueFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3, 4);
    
    // Act
    int withBlue = ColorUtils.setBlueToRGBA(color.getRGBA(), 0);
    
    // Assert    
    assertThat(ColorUtils.getBlueFromRGBA(withBlue)).isEqualTo(0);
  }
  
  @Test
  void testSetAlphaFromRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3, 4);
    
    // Act
    int withAlpha = ColorUtils.setAlphaToRGBA(color.getRGBA(), 0);
    
    // Assert    
    assertThat(ColorUtils.getAlphaFromRGBA(withAlpha)).isEqualTo(0);
  }
  
  @Test
  void testMulRGBA() {
    // Arrange
    MulderColor color = new MulderColor(1, 2, 3, 4);
    
    // Act
    int result = ColorUtils.mulRGBA(color.getRGBA(), 5);
    
    // Assert    
    assertThat(result).isEqualTo(new MulderColor(5, 10, 15, 20).getRGBA());
  }
  
  @Test
  void testAddRGBA() {
    // Arrange
    MulderColor colorA = new MulderColor(1, 2, 3, 4);
    MulderColor colorB = new MulderColor(5, 6, 7, 8);
    
    // Act
    int result = ColorUtils.addRGBA(colorA.getRGBA(), colorB.getRGBA());
    
    // Assert    
    assertThat(result).isEqualTo(new MulderColor(6, 8, 10, 12).getRGBA());
  }

}
