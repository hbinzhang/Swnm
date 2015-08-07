package com.service.sounddev.thirdsection;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public  class _StreamData extends Structure {
	/**
	 * DI/DO data for Slot0, Slot1,...., Slot7<br>
	 * C type : WORD[8]
	 */
	public short[] DIO = new short[8];
	/**
	 * AI/AO data for slot0<br>
	 * C type : WORD[8]
	 */
	public short[] Slot0 = new short[8];
	/**
	 * AI/AO data for slot1<br>
	 * C type : WORD[8]
	 */
	public short[] Slot1 = new short[8];
	/**
	 * AI/AO data for slot2<br>
	 * C type : WORD[8]
	 */
	public short[] Slot2 = new short[8];
	/**
	 * AI/AO data for slot3<br>
	 * C type : WORD[8]
	 */
	public short[] Slot3 = new short[8];
	/**
	 * AI/AO data for slot4<br>
	 * C type : WORD[8]
	 */
	public short[] Slot4 = new short[8];
	/**
	 * AI/AO data for slot5<br>
	 * C type : WORD[8]
	 */
	public short[] Slot5 = new short[8];
	/**
	 * AI/AO data for slot6<br>
	 * C type : WORD[8]
	 */
	public short[] Slot6 = new short[8];
	/**
	 * AI/AO data for slot6<br>
	 * C type : WORD[8]
	 */
	public short[] Slot7 = new short[8];
	public _StreamData() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("DIO", "Slot0", "Slot1", "Slot2", "Slot3", "Slot4", "Slot5", "Slot6", "Slot7");
	}
	/**
	 * @param DIO DI/DO data for Slot0, Slot1,...., Slot7<br>
	 * C type : WORD[8]<br>
	 * @param Slot0 AI/AO data for slot0<br>
	 * C type : WORD[8]<br>
	 * @param Slot1 AI/AO data for slot1<br>
	 * C type : WORD[8]<br>
	 * @param Slot2 AI/AO data for slot2<br>
	 * C type : WORD[8]<br>
	 * @param Slot3 AI/AO data for slot3<br>
	 * C type : WORD[8]<br>
	 * @param Slot4 AI/AO data for slot4<br>
	 * C type : WORD[8]<br>
	 * @param Slot5 AI/AO data for slot5<br>
	 * C type : WORD[8]<br>
	 * @param Slot6 AI/AO data for slot6<br>
	 * C type : WORD[8]<br>
	 * @param Slot7 AI/AO data for slot6<br>
	 * C type : WORD[8]
	 */
	public _StreamData(short DIO[], short Slot0[], short Slot1[], short Slot2[], short Slot3[], short Slot4[], short Slot5[], short Slot6[], short Slot7[]) {
		super();
		if ((DIO.length != this.DIO.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.DIO = DIO;
		if ((Slot0.length != this.Slot0.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot0 = Slot0;
		if ((Slot1.length != this.Slot1.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot1 = Slot1;
		if ((Slot2.length != this.Slot2.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot2 = Slot2;
		if ((Slot3.length != this.Slot3.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot3 = Slot3;
		if ((Slot4.length != this.Slot4.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot4 = Slot4;
		if ((Slot5.length != this.Slot5.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot5 = Slot5;
		if ((Slot6.length != this.Slot6.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot6 = Slot6;
		if ((Slot7.length != this.Slot7.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.Slot7 = Slot7;
	}
	public _StreamData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends _StreamData implements Structure.ByReference {
		
	};
	public static class ByValue extends _StreamData implements Structure.ByValue {
		
	};
}
