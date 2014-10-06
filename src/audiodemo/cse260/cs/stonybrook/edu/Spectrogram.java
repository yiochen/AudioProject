package audiodemo.cse260.cs.stonybrook.edu;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * representing the spectrogram in array form
 * 
 * @author Yiou
 *
 */
public class Spectrogram {
	private double[][] values;
	private final int length;
	private final int sizePerFrame;
	private double max = 0;

	private Spectrogram(double[][] values) {
		length = values.length;
		this.values=values;
		sizePerFrame = (values[0] != null) ? values[0].length /2: 0;
	}

	/**
	 * return the length of time
	 * 
	 * @return length then amount to total frames
	 */
	public int length() {
		return length;
	}

	/**
	 * return the amount of coefficients, or amount of different powers
	 * 
	 * @return
	 */
	public int sizePerFrame() {
		return sizePerFrame;
	}

	/**
	 * Compute the max power
	 * 
	 * @return
	 */
	public double getMaxPower() {
		if (max > 0)
			return max;
		else {
			for (int i = 0; i < length(); i++) {
				for (int k = 0; k < sizePerFrame(); k++) {
					if (max < getPower(i, k))
						max = getPower(i, k);
				}
			}
			return max;
		}
	}

	/**
	 * return the sum of squares of real part and imaginary part
	 * 
	 * @param time
	 * @param k
	 * @return
	 */
	public double getPower(int time, int k) {
		double a = values[time][k * 2];
		double b = values[time][k * 2 + 1];
		return Math.pow(a, 2) + Math.pow(b, 2);
	}

	/**
	 * return the amplitude of the kth sin wave
	 * 
	 * @param time
	 * @param k
	 * @return
	 */
	public double getReal(int time, int k) {
		return values[time][2 * k];
	}

	/**
	 * return the amplitude of the kth cosin wave
	 * 
	 * @param time
	 * @param k
	 * @return
	 */
	public double getImag(int time, int k) {
		return values[time][2 * k + 1];
	}

	public static Spectrogram fromClip(AudioClip clip, int sizePerFrame) {
		DoubleFFT_1D doubleFFT = new DoubleFFT_1D(sizePerFrame);
		double[][] values = new double[clip.length()][sizePerFrame * 2];
		for (int i = 0; i < clip.length(); i++) {
			for (int j = 0; j < sizePerFrame && i + j < clip.length(); j++) {
				values[i][j] = clip.getSample(i + j);
			}
			doubleFFT.complexForward(values[i]);

		}
		return new Spectrogram(values);
	}

}
