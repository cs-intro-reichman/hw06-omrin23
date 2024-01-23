// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		// Color[][] tinypic = read("tinypic.ppm");
		// print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		// Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		// imageOut = flippedVertically(tinypic);
		// System.out.println();
		// print(imageOut);
		
		//testing functions
		// System.out.println();
		// print(scaled(tinypic, 3, 5));
		// System.out.println(blend(new Color(100, 40, 100), new Color(200, 20, 40), 0.25));
	}
	
	//returns all the colors in the given line
	//in a form of an array
	public static Color[] getLine(String line, int length) {
		String[] rgbValues = line.split("\\s+");
		Color[] rgbColors = new Color[length / 3];
		int rVal, gVal, bVal, index = 0;
		int colorsIndex = 0;
		if (rgbValues[0] == "") {
			index++;
		}

		for (int i = index; i < rgbValues.length; i++) {
			rVal = Integer.parseInt(rgbValues[i]);
			gVal = Integer.parseInt(rgbValues[i + 1]);
			bVal = Integer.parseInt(rgbValues[i + 2]);
			rgbColors[colorsIndex] = new Color(rVal, gVal, bVal);
			colorsIndex++;
			i += 2;
		}

		return rgbColors;
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		Color[] colorsLine;
		int currentRow = 0;
		String currentLine = "";
		in.readLine();
		
		while (!in.isEmpty()) {
			currentLine = in.readLine();
			
			if (currentLine != null) {
				colorsLine = getLine(currentLine, numCols * 3);
				image[currentRow] = colorsLine;
				currentRow++;
			}
		}


		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {

		int rows = image.length;
		int colls = image[0].length;
		
		for (int i = 0; i < rows; i++) { 
			for (int j = 0; j < colls; j++) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {

		int rows = image.length;
		int colls = image[0].length;
		int flippedIndex = 0;
		Color[][] flippedImage = new Color[rows][colls];

		for (int i = 0; i < rows; i++) {
			flippedIndex = 0;
			for (int j = colls - 1; j >= 0; j--) {
				flippedImage[i][flippedIndex] = image[i][j];
				flippedIndex++;
			}
		}


		return flippedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		
		int rows = image.length;
		int colls = image[0].length;
		int flippedIndex = 0;
		Color[][] flippedImage = new Color[rows][colls];

		for (int i = 0; i < colls; i++) {
			flippedIndex = 0;
			for (int j = rows - 1; j >= 0; j--) {
				flippedImage[flippedIndex][i] = image[j][i];
				flippedIndex++;
			}
		}


		return flippedImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
		int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		
		return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		
		int rows = image.length;
		int colls = image[0].length;
		Color[][] grayScaledImage = new Color[rows][colls];

		for (int i = 0; i < rows; i++) { 
			for (int j = 0; j < colls; j++) {
				grayScaledImage[i][j] = luminance(image[i][j]);
			}
		}

		return grayScaledImage;
	}

	//returns the scaled index
	public static int getScaledIndex(int index, int source, int target) {
		return (int)((double)index * ((double) source /(double) target));
	}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		
		int sourceWidth = image[0].length;
		int sourceHeight = image.length;
		Color[][] scaledImage = new Color[height][width];

		for (int i = 0; i < scaledImage.length; i++) {
			for (int j = 0; j < scaledImage[0].length; j++) {
				scaledImage[i][j] = image[getScaledIndex(i, sourceHeight, height)][getScaledIndex(j, sourceWidth, width)];
			}
		}

		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		
		double cOneR = (double) c1.getRed();
		double cOneG = (double) c1.getGreen();
		double cOneB = (double) c1.getBlue();
		double cTwoR = (double) c2.getRed();
		double cTwoG = (double) c2.getGreen();
		double cTwoB = (double) c2.getBlue();
		int blendR = (int) ((alpha * cOneR) + ((1.0 - alpha) * cTwoR));
		int blendG = (int) ((alpha * cOneG) + ((1.0 - alpha) * cTwoG));
		int blendB = (int) ((alpha * cOneB) + ((1.0 - alpha) * cTwoB));

		return new Color(blendR, blendG, blendB);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		
		int rows = image1.length;
		int colls = image1[0].length;
		Color[][] blendIamge = new Color[rows][colls];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < colls; j++) {
				blendIamge[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}

		return blendIamge;
	}

	//morphs the image and in each iteration
	//makes it a bit more grayish
	public static void morphGrayScaled(Color[][] image, int n) {

		double alpha;

		for (int i = n; i >= 0; i--) {
			alpha = (double) ( (double)(n - i) / (double)n );

			image = blend(grayScaled(image), image, alpha);

			// Displays the image
			Runigram.setCanvas(image);
			Runigram.display(image);
			StdDraw.pause(500);
		}


	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		
		int sourceRows = source.length;
		int sourceColls = source[0].length;
		int targetRows = target.length;
		int targetColls = target[0].length;
		Color[][] morphedImage = new Color[sourceRows][sourceColls];
		double alpha;

		if (sourceRows != targetRows || sourceColls != targetColls) {
			target = scaled(target, sourceColls, sourceRows);
		}

		for (int i = n; i >= 0; i--) {
			alpha = (double) ( (double)(n - i) / (double)n );

			morphedImage = blend(target, source, alpha);

			// Displays the image
			Runigram.setCanvas(morphedImage);
			Runigram.display(morphedImage);
			StdDraw.pause(500);
		}

	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

