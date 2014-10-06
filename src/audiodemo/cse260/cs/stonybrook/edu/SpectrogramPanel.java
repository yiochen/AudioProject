package audiodemo.cse260.cs.stonybrook.edu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class SpectrogramPanel extends JPanel {
	private Spectrogram spect;
	private double hzoom;
	private int sizePerFrame;
	
	/**
	 * Initialize a SpectrogramPanel.
	 * @param clip
	 */
	public SpectrogramPanel(AudioClip clip, int sizePerFrame){
		spect=Spectrogram.fromClip(clip, sizePerFrame);
		this.sizePerFrame=sizePerFrame;
		setZoom(8.0);
	}
	public void setZoom(double zoom){
		this.hzoom=zoom;
		int width = (int)(spect.length() / hzoom);
		int height = sizePerFrame;
		setPreferredSize(new Dimension(width, height));
		revalidate();
	}
	public double getZoom(){
		return this.hzoom;
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Dimension d=getSize();
		long length=spect.length();
		double samplesPerPixel=(double)length/d.getWidth();
		hzoom=samplesPerPixel;
		long height=spect.sizePerFrame();
		Rectangle bounds=g.getClipBounds();
		int startX=(int)bounds.getX();
		int endX=startX+(int)bounds.getWidth();
		double i=startX*samplesPerPixel;
		
		for (int x=0;x<spect.length();x++){
			for (int k=0;k<spect.sizePerFrame();k++){
				int pow=(int)spect.getPower(x,k);
				float cRatio=(float)(pow/spect.getMaxPower());
				g.setColor(new Color(cRatio, cRatio, cRatio));
				System.out.print(pow+" ");
				g.drawLine(x, k, x+5, k);
			}
		}
	}
}
