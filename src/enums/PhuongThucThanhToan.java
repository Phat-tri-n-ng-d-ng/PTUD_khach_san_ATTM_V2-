package enums;

import java.util.Iterator;

public enum PhuongThucThanhToan {
	TienMat,
    ChuyenKhoan;
	
	//chuyen tu enum sang String
	public static PhuongThucThanhToan fromString(String pTTTString) {
		// TODO Auto-generated method stub
		for(PhuongThucThanhToan ptt : PhuongThucThanhToan.values()) {
			if(ptt.name().equals(pTTTString)) {
				return ptt;
			}
			
		}
		return TienMat;
	}
    
    
}
