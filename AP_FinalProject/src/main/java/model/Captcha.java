package model;

import java.util.ArrayList;
import java.util.Random;
import model.enums.CaptchaNumbers;

public class Captcha {
    
    private final ArrayList<Integer> numbers = new ArrayList<>();


    public Captcha(){
        setNumbersList();
    }

    private void setNumbersList(){
        Random random = new Random();
        int noOfDigits = random.nextInt(4, 8);
        for (int i = 0; i < noOfDigits; i++) {
            int noToBeAdded = random.nextInt(0, 9);
            numbers.add(noToBeAdded);
        }
    }

    public String generateCaptcha(){
        String captcha = "";
        for (int i = 0; i < 5; i++) {
            for (Integer integer : numbers) {
                String noSaver = getArtFromDigit(integer);
                String[] lineSaver = noSaver.split("\n");
                captcha = captcha.concat(lineSaver[i]);
                captcha = captcha.concat(" ");
            }
            captcha = captcha.concat("\n");
        }
        captcha = addNoise(captcha);
        return captcha;
    }
    private String getArtFromDigit(int digit) {
        return switch (digit) {
            case 0 -> CaptchaNumbers.ZERO.getAsciiArt();
            case 1 -> CaptchaNumbers.ONE.getAsciiArt();
            case 2 -> CaptchaNumbers.TWO.getAsciiArt();
            case 3 -> CaptchaNumbers.THREE.getAsciiArt();
            case 4 -> CaptchaNumbers.FOUR.getAsciiArt();
            case 5 -> CaptchaNumbers.FIVE.getAsciiArt();
            case 6 -> CaptchaNumbers.SIX.getAsciiArt();
            case 7 -> CaptchaNumbers.SEVEN.getAsciiArt();
            case 8 -> CaptchaNumbers.EIGHT.getAsciiArt();
            case 9 -> CaptchaNumbers.NINE.getAsciiArt();
            default -> CaptchaNumbers.EMPTY.getAsciiArt();
        };
    }

    private String addNoise (String originalCaptcha){
        int stringLocation = 0;
        Random random = new Random();
        while (stringLocation < originalCaptcha.length()-8) {
            int randomNumb = random.nextInt(3, 8);
            stringLocation += randomNumb;

            if (originalCaptcha.charAt(stringLocation) == '\n'){
                continue;
            }

            originalCaptcha = originalCaptcha.substring(0, stringLocation) + "." + originalCaptcha.substring(stringLocation + 1);
        }
        return originalCaptcha;
    }

    public int getTheOriginalCode(){
        String code = "";
        for (Integer integer : numbers){
            code = code.concat(integer.toString());
        }
        return Integer.parseInt(code);
    }


}
