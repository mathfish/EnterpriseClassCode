package thompson.enterprise.design.hw1;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GreetingTest {

    private Response impl;
    public GreetingTest(){
        impl = new Response();
    }

    @Test
    public void respondToGreetingHelloTest(){
        String response = impl.respondToGreeting(Greeting.HELLO);
        assertEquals("Hi",response);
    }

    @Test
    public void respondToGreetingGreetingTest(){
        String response = impl.respondToGreeting(Greeting.GREETING);
        assertEquals("Salutations",response);
    }

    @Test
    public void respondToGreetingOtherTest(){
        String response = impl.respondToGreeting(Greeting.HOLA);
        assertEquals("Sorry, I didn't hear you",response);
    }

    @Test
    public void respondToHowAreYouFeelingFineTest(){
        String response = impl.respondToHowAreYou(Feeling.FINE);
        assertEquals("I'm glad to hear that", response);
    }

    @Test
    public void respondToHowAreYouFeelingTiredTest(){
        String response = impl.respondToHowAreYou(Feeling.TIRED);
        assertEquals("Maybe you should take a vacation", response);
    }

    @Test
    public void respondToHowAreYouFeelingOtherTest(){
        String response = impl.respondToHowAreYou(Feeling.NOTSURE);
        assertEquals("I'm not familiar with that condition. Is it serious?", response);
    }

    @Test
    public void respondToHowIsTheWeatherSunnyOver70(){
        String response = impl.respondToHowIsTheWeather(71, SkyCondition.SUNNY);
        assertEquals("It's beautiful out today - warm and sunny", response);
    }

    @Test
    public void respondToHowIsTheWeatherSunnyUnder70(){
        String response = impl.respondToHowIsTheWeather(69, SkyCondition.SUNNY);
        assertEquals("It's sunny out, but a little cooler than I'd like", response);
    }

    @Test
    public void respondToHowIsTheWeatherRainyOver70(){
        String response = impl.respondToHowIsTheWeather(71, SkyCondition.RAINY);
        assertEquals("It's rainy, but at least it's warm out!", response);
    }

    @Test
    public void respondToHowIsTheWeatherRainyUnder70(){
        String response = impl.respondToHowIsTheWeather(69, SkyCondition.RAINY);
        assertEquals("It's rainy and chilly, a good day to stay inside", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBasic(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("He is doing well, thank you.", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBirthDayComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+16));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Jim's birthday coming up? Please wish him a happy birthday for me.", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBirthDayPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have a birthday? Please wish him a happy birthday for me.", response);
    }

    @Test
    public void respondHowIsYourSpouseHeAnniversaryComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't your anniversary coming up? Happy anniversary!", response);
    }

    @Test
    public void respondHowIsYourSpouseHeAnniversaryPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have an anniversary? Happy anniversary to you both!", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBirthDayAndAnniversaryComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Jim's birthday coming up? Please wish him a happy birthday for me." +
                " And isn't your anniversary coming up? Happy anniversary!", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBirthDayAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have a birthday? Please wish him a happy birthday for me." +
                " And didn't Jim just have an anniversary? Happy anniversary to you both!", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBirthDayComingAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Jim's birthday coming up? Please wish him a happy birthday for me." +
                " And didn't Jim just have an anniversary? Happy anniversary to you both!", response);
    }

    @Test
    public void respondHowIsYourSpouseHeBirthDayPastAndAnniversaryComing() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have a birthday? Please wish him a happy birthday for me." +
                " And isn't your anniversary coming up? Happy anniversary!", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBasic(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("She is doing well, thank you.", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBirthDayComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Mary's birthday coming up? Please wish her a happy birthday for me.", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBirthDayPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have a birthday? Please wish her a happy birthday for me.", response);
    }

    @Test
    public void respondHowIsYourSpouseSheAnniversaryPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have an anniversary? Happy anniversary to you both!", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBirthDayAndAnniversaryComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Mary's birthday coming up? Please wish her a happy birthday for me. " +
                "And isn't your anniversary coming up? Happy anniversary!", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBirthDayAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have a birthday? Please wish her a happy birthday for me. " +
                "And didn't Mary just have an anniversary? Happy anniversary to you both!", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBirthDayComingAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Mary's birthday coming up? Please wish her a happy birthday for me." +
                " And didn't Mary just have an anniversary? Happy anniversary to you both!", response);
    }

    @Test
    public void respondHowIsYourSpouseSheBirthDayPastAndAnniversaryComing() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have a birthday? Please wish her a happy birthday for me." +
                " And isn't your anniversary coming up? Happy anniversary!", response);
    }
}
