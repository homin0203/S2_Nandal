package kh.s2.nandal.classdata.model.vo;

public class ClassVo {
//	CLASS_CODE    NOT NULL NUMBER         
//	CATEGORY_CODE NOT NULL NUMBER(2)      
//	CLASS_NAME    NOT NULL VARCHAR2(200)  
//	CLASS_IMG     NOT NULL VARCHAR2(100)  
//	CLASS_INTRO   NOT NULL VARCHAR2(4000) 
//	CLASS_CUR     NOT NULL VARCHAR2(3000) 
//	CLASS_HOST    NOT NULL VARCHAR2(2000) 
//	CLASS_ALLTIME NOT NULL VARCHAR2(30)   
//	CLASS_PRD              VARCHAR2(1000)  
//	AREA_CODE     NOT NULL NUMBER(2)      
//	CLASS_ADRESS  NOT NULL VARCHAR2(300)  
//	CLASS_PRICE   NOT NULL NUMBER(10)     
//	CLASS_LEVEL   NOT NULL NUMBER(1)      
//	CLASS_MIN     NOT NULL NUMBER(2)      
//	CLASS_MAX     NOT NULL NUMBER(2) 
	private int classCode;
	private int categoryCode;
	private String className;
	private String classImg;
	private String classIntro;
	private String classCur;
	private String classHost;
	private String classAlltime;
	private String classPrd;
	private int areaCode;
	private String classAddress;
	private int classPrice;
	private int classLevel;
	private int classMin;
	private int classMax;
	private double allAvg;
	private int allCnt;
	
	public ClassVo() {
		super();
	}

	public ClassVo(int classCode, int categoryCode, String className, String classImg, String classIntro,
			String classCur, String classHost, String classAlltime, String classPrd, int areaCode, String classAddress,
			int classPrice, int classLevel, int classMin, int classMax) {
		super();
		this.classCode = classCode;
		this.categoryCode = categoryCode;
		this.className = className;
		this.classImg = classImg;
		this.classIntro = classIntro;
		this.classCur = classCur;
		this.classHost = classHost;
		this.classAlltime = classAlltime;
		this.classPrd = classPrd;
		this.areaCode = areaCode;
		this.classAddress = classAddress;
		this.classPrice = classPrice;
		this.classLevel = classLevel;
		this.classMin = classMin;
		this.classMax = classMax;
	}


	@Override
	public String toString() {
		return "ClassVo [classCode=" + classCode + ", categoryCode=" + categoryCode + ", className=" + className
				+ ", classImg=" + classImg + ", classIntro=" + classIntro + ", classCur=" + classCur + ", classHost="
				+ classHost + ", classAlltime=" + classAlltime + ", classPrd=" + classPrd + ", areaCode=" + areaCode
				+ ", classAddress=" + classAddress + ", classPrice=" + classPrice + ", classLevel=" + classLevel
				+ ", classMin=" + classMin + ", classMax=" + classMax + ", allAvg=" + allAvg + ", allCnt=" + allCnt
				+ "]";
	}

	public int getClassCode() {
		return classCode;
	}

	public void setClassCode(int classCode) {
		this.classCode = classCode;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassImg() {
		return classImg;
	}

	public void setClassImg(String classImg) {
		this.classImg = classImg;
	}

	public String getClassIntro() {
		return classIntro;
	}

	public void setClassIntro(String classIntro) {
		this.classIntro = classIntro;
	}

	public String getClassCur() {
		return classCur;
	}

	public void setClassCur(String classCur) {
		this.classCur = classCur;
	}

	public String getClassHost() {
		return classHost;
	}

	public void setClassHost(String classHost) {
		this.classHost = classHost;
	}

	public String getClassAlltime() {
		return classAlltime;
	}

	public void setClassAlltime(String classAlltime) {
		this.classAlltime = classAlltime;
	}

	public String getClassPrd() {
		return classPrd;
	}

	public void setClassPrd(String classPrd) {
		this.classPrd = classPrd;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}

	public String getClassAddress() {
		return classAddress;
	}

	public void setClassAddress(String classAddress) {
		this.classAddress = classAddress;
	}

	public int getClassPrice() {
		return classPrice;
	}

	public void setClassPrice(int classPrice) {
		this.classPrice = classPrice;
	}

	public int getClassLevel() {
		return classLevel;
	}

	public void setClassLevel(int classLevel) {
		this.classLevel = classLevel;
	}

	public int getClassMin() {
		return classMin;
	}

	public void setClassMin(int classMin) {
		this.classMin = classMin;
	}

	public int getClassMax() {
		return classMax;
	}

	public void setClassMax(int classMax) {
		this.classMax = classMax;
	}

	public double getAllAvg() {
		return allAvg;
	}

	public void setAllAvg(double allAvg) {
		this.allAvg = allAvg;
	}

	public int getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(int allCnt) {
		this.allCnt = allCnt;
	}
}
