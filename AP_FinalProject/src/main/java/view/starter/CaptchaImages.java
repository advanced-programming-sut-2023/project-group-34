package view.starter;

public enum CaptchaImages {

    A1181(CaptchaImages.class.getResource("/images/captcha/1181.png").toString(), 1181),
    A1381(SecurityAndCaptchaRegister.class.getResource("/images/captcha/1381.png").toString(), 1381),
    A1491(SecurityAndCaptchaRegister.class.getResource("/images/captcha/1491.png").toString(), 1491),
    A1722(SecurityAndCaptchaRegister.class.getResource("/images/captcha/1722.png").toString(), 17221),
    A1959(SecurityAndCaptchaRegister.class.getResource("/images/captcha/1959.png").toString(), 1959),
    A2163(SecurityAndCaptchaRegister.class.getResource("/images/captcha/2163.png").toString(), 2163),
    A2177(SecurityAndCaptchaRegister.class.getResource("/images/captcha/2177.png").toString(), 2177),
    A2723(SecurityAndCaptchaRegister.class.getResource("/images/captcha/2723.png").toString(), 2723),
    A2785(SecurityAndCaptchaRegister.class.getResource("/images/captcha/2785.png").toString(), 2785),
    A3541(SecurityAndCaptchaRegister.class.getResource("/images/captcha/3541.png").toString(), 3541),
    A3847(SecurityAndCaptchaRegister.class.getResource("/images/captcha/3847.png").toString(), 3847),
    A3855(SecurityAndCaptchaRegister.class.getResource("/images/captcha/3855.png").toString(), 3855),
    A3876(SecurityAndCaptchaRegister.class.getResource("/images/captcha/3876.png").toString(), 3876),
    A3967(SecurityAndCaptchaRegister.class.getResource("/images/captcha/3967.png").toString(), 3967),
    A4185(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4185.png").toString(), 4185),
    A4310(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4310.png").toString(), 4310),
    A4487(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4487.png").toString(), 4487),
    A4578(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4578.png").toString(), 4578),
    A4602(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4602.png").toString(), 4602),
    A4681(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4681.png").toString(), 4681),
    A4924(SecurityAndCaptchaRegister.class.getResource("/images/captcha/4924.png").toString(), 4924),
    A5326(SecurityAndCaptchaRegister.class.getResource("/images/captcha/5326.png").toString(), 5326),
    A5463(SecurityAndCaptchaRegister.class.getResource("/images/captcha/5463.png").toString(), 5463),
    A5771(SecurityAndCaptchaRegister.class.getResource("/images/captcha/5771.png").toString(), 5771),
    A5849(SecurityAndCaptchaRegister.class.getResource("/images/captcha/5849.png").toString(), 5849),
    A6426(SecurityAndCaptchaRegister.class.getResource("/images/captcha/6426.png").toString(), 6426),
    A6553(SecurityAndCaptchaRegister.class.getResource("/images/captcha/6553.png").toString(), 6553),
    A6601(SecurityAndCaptchaRegister.class.getResource("/images/captcha/6601.png").toString(), 6601),
    A6733(SecurityAndCaptchaRegister.class.getResource("/images/captcha/6733.png").toString(), 6733),
    A6960(SecurityAndCaptchaRegister.class.getResource("/images/captcha/6960.png").toString(), 6960),
    A7415(SecurityAndCaptchaRegister.class.getResource("/images/captcha/7415.png").toString(), 7415),
    A7609(SecurityAndCaptchaRegister.class.getResource("/images/captcha/7609.png").toString(), 7609),
    A7755(SecurityAndCaptchaRegister.class.getResource("/images/captcha/7755.png").toString(), 7755),
    A7825(SecurityAndCaptchaRegister.class.getResource("/images/captcha/7825.png").toString(), 7825),
    A7905(SecurityAndCaptchaRegister.class.getResource("/images/captcha/7905.png").toString(), 7905),
    A8003(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8003.png").toString(), 8003),
    A8070(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8070.png").toString(), 8070),
    A8368(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8368.png").toString(), 8368),
    A8455(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8455.png").toString(), 8455),
    A8506(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8506.png").toString(), 8506),
    A8555(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8555.png").toString(), 8555),
    A8583(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8583.png").toString(), 8583),
    A8692(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8692.png").toString(), 8692),
    A8776(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8776.png").toString(), 8776),
    A8996(SecurityAndCaptchaRegister.class.getResource("/images/captcha/8996.png").toString(), 8996),
    A9061(SecurityAndCaptchaRegister.class.getResource("/images/captcha/9061.png").toString(), 9061),
    A9386(SecurityAndCaptchaRegister.class.getResource("/images/captcha/9386.png").toString(), 9386),
    A9582(SecurityAndCaptchaRegister.class.getResource("/images/captcha/9582.png").toString(), 9582),
    A9633(SecurityAndCaptchaRegister.class.getResource("/images/captcha/9633.png").toString(), 9633);


    private int number;
    private String link;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    CaptchaImages(String link, int number){
        this.link = link;
        this.number = number;
    }
}
