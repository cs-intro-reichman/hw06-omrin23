import java.awt.Color;

/**
 * takes an image and number of iterations
 * in each iteration it shows the picture a bit
 * more gray until the picture is fully gray at the end
 */
public class Editor4 {

	public static void main (String[] args) {
		if (args.length > 0) {
			String imageFile = args[0];
			int n = Integer.parseInt(args[1]);
			Color[][] image = Runigram.read(imageFile);
            Runigram.setCanvas(image);
			Runigram.morphGrayScaled(image, n);
		}
	}
}
