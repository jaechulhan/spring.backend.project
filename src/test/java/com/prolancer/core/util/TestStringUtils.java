/**
 * 
 */
package com.prolancer.core.util;

import com.prolancer.core.common.utils.StringUtils;

/**
 * @author jaechulhan
 *
 */
public class TestStringUtils {

	public static final String encoding = "UTF-8";

	public static void main(String[] args) throws Exception {
		String s = "123456789 a s d f g h j k l ; ' q w e r t y u i o p [ ] \\ 1 2 3 4 5 6 7 8 9 0 - = as df gh jk l; zx cv bn m, ./ qw y ui op []"
				+ "qwe rty uio p[] asd fgh jkl zxc vbn m,. 123 456 789 1234 5678 90-= qwer tyui op[] asdf ghjk l;zx cvbn m,./"
				+ "12345 67890 qwert yuiop asdfg hjklz xcvbn m,khg er xcgvdst 453 gd fyrt634 5 dg e653 545u7r ydf dgfsd fsart sdgdsg"
				+ "sd fsdjf;sjduw[r sldfjsl;djrf2345203v sdfs'wse[rftu 2[3r50 j;lgjks'drf 2354-0u sadfas';dfmka][ ert02u9wsd's;lad fa'sdfjasdgf"
				+ "4 wr[w09u jsd' af32 'QWRJE ajfsdj :ejwkiiuUUSJ W[9230 U'SFJKS JFJSF 'ASDP;GF SDFAW435]2 JKSD';GDALSJ GF'SDAGJFMA'L 2UWQ 'PS;DGJM"
				+ "ju=[0a9 u0wr w435r]2345ru]2tu  s'adgjfa'sgf2uq]34u]23'g jfq4323o4 23w 52]5-2t4ewsjtrg'qwpe[fa7 2   03r/ dshgf"
				+ "djl3r4 sjdf ;wq3r q3ra[d0aw'tj3 wr[w09u asd fgh zxc er gd swser9[0345r]2 3dfjsdfsd ]23-qg';gvas 'dgawejrt ]23trwasdg;mas'fgsnkd gasf "
				+ "[w90rtusdmgkf zxc sadfs fa fsart sdgdsg wr JFJSF GF fgh xcgvdst dfmka SDAGJFMA 2345ru wse[rftu 2[3r50 j;l af32 sdfjasdgf jks'drf 2354-"
				+ "djrf2345203v KSD';GDALSJ GF'";

		String compressed = StringUtils.compress(s);
		Integer savedLength = s.length() - compressed.length();
		Double saveRatio = (new Double(savedLength) * 100) / s.length();
		String ratioString = saveRatio.toString() + "00000000";
		ratioString = ratioString.substring(0, ratioString.indexOf(".") + 4);
		System.out.println("Original_String_Length=" + s.length());
		System.out.println("Compressed_String_Length=" + compressed.length() + ", Compression_Ratio=" + ratioString + "%");

		String decompressed = StringUtils.decompress(StringUtils.decodeBase64(compressed));
		System.out.println("Decompressed_String_Length=" + decompressed.length() + " == Original_String_Length (" + s.length() + ")");
		System.out.println("Original_String == Decompressed_String=" + (s.equals(decompressed) ? "True" : "False"));
	}

}
