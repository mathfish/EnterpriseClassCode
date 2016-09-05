package thompson.enterprise.design.hw1;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class GreetingTest {

    private Response impl;
    public GreetingTest(){
        impl = new Response();
    }

    //Test respondToGreeting method with HELLO enum of Greeting
    @Test
    public void respondToGreetingHelloTest(){
        String response = impl.respondToGreeting(Greeting.HELLO);
        assertEquals("Hi",response);
    }

    //Test respondToGreeting method with GREETING enum of Greeting
    @Test
    public void respondToGreetingGreetingTest(){
        String response = impl.respondToGreeting(Greeting.GREETING);
        assertEquals("Salutations",response);
    }

    //Test respondToGreetingOther method with HOLA enum of Greeting for default response
    @Test
    public void respondToGreetingOtherTest(){
        String response = impl.respondToGreeting(Greeting.HOLA);
        assertEquals("Sorry, I didn't hear you",response);
    }

    //Test respondToHowAreYouFeeling with FINE enum of Feeling
    @Test
    public void respondToHowAreYouFeelingFineTest(){
        String response = impl.respondToHowAreYou(Feeling.FINE);
        assertEquals("I'm glad to hear that", response);
    }

    //Test respondToHowAreYouFeeling with TIRED enum of Feeling
    @Test
    public void respondToHowAreYouFeelingTiredTest(){
        String response = impl.respondToHowAreYou(Feeling.TIRED);
        assertEquals("Maybe you should take a vacation", response);
    }

    //Test respondToHowAreYouFeeling with NOTSURE enum of Feeling for default response
    @Test
    public void respondToHowAreYouFeelingOtherTest(){
        String response = impl.respondToHowAreYou(Feeling.NOTSURE);
        assertEquals("I'm not familiar with that condition. Is it serious?", response);
    }

    //Test respondToHowIsTheWeather when sunny and over 70
    @Test
    public void respondToHowIsTheWeatherSunnyOver70(){
        String response = impl.respondToHowIsTheWeather(71, SkyCondition.SUNNY);
        assertEquals("It's beautiful out today - warm and sunny", response);
    }

    //Test respondToHowIsTheWeather when sunny and under 70
    @Test
    public void respondToHowIsTheWeatherSunnyUnder70(){
        String response = impl.respondToHowIsTheWeather(69, SkyCondition.SUNNY);
        assertEquals("It's sunny out, but a little cooler than I'd like", response);
    }

    //Test respondToHowIsTheWeather when rainy and over 70
    @Test
    public void respondToHowIsTheWeatherRainyOver70(){
        String response = impl.respondToHowIsTheWeather(71, SkyCondition.RAINY);
        assertEquals("It's rainy, but at least it's warm out!", response);
    }

    //Test respondToHowIsTheWeather when rainy and under 70
    @Test
    public void respondToHowIsTheWeatherRainyUnder70(){
        String response = impl.respondToHowIsTheWeather(69, SkyCondition.RAINY);
        assertEquals("It's rainy and chilly, a good day to stay inside", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday or anniversary event doesn't happen
    @Test
    public void respondHowIsYourSpouseHeBasic(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("He is doing well, thank you.", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday upcoming event happens
    @Test
    public void respondHowIsYourSpouseHeBirthDayComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+16));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Jim's birthday coming up? Please wish him a happy birthday for me.", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday past event happens
    @Test
    public void respondHowIsYourSpouseHeBirthDayPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have a birthday? Please wish him a happy birthday for me.", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and anniversary upcoming event happens
    @Test
    public void respondHowIsYourSpouseHeAnniversaryComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't your anniversary coming up? Happy anniversary!", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and anniversary past event happens
    @Test
    public void respondHowIsYourSpouseHeAnniversaryPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have an anniversary? Happy anniversary to you both!", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday and anniversary upcoming events happen
    @Test
    public void respondHowIsYourSpouseHeBirthDayAndAnniversaryComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Jim's birthday coming up? Please wish him a happy birthday for me." +
                " And isn't your anniversary coming up? Happy anniversary!", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday and anniversary past events happen
    @Test
    public void respondHowIsYourSpouseHeBirthDayAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have a birthday? Please wish him a happy birthday for me." +
                " And didn't Jim just have an anniversary? Happy anniversary to you both!", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday upcoming and anniversary past events happens
    @Test
    public void respondHowIsYourSpouseHeBirthDayComingAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Jim's birthday coming up? Please wish him a happy birthday for me." +
                " And didn't Jim just have an anniversary? Happy anniversary to you both!", response);
    }

    //Test respondHowIsYourSpouse when spouse is male and birthday past and anniversary upcoming events happens
    @Test
    public void respondHowIsYourSpouseHeBirthDayPastAndAnniversaryComing() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Jim", Sex.M, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Jim just have a birthday? Please wish him a happy birthday for me." +
                " And isn't your anniversary coming up? Happy anniversary!", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday or anniversary event doesn't happen
    @Test
    public void respondHowIsYourSpouseSheBasic(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("She is doing well, thank you.", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday upcoming event happens
    @Test
    public void respondHowIsYourSpouseSheBirthDayComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Mary's birthday coming up? Please wish her a happy birthday for me.", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday past event happens
    @Test
    public void respondHowIsYourSpouseSheBirthDayPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+15));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have a birthday? Please wish her a happy birthday for me.", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and anniversary past event happens. Anniversary upcoming has
    // same functionality for male or female spouse
    @Test
    public void respondHowIsYourSpouseSheAnniversaryPast(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-16),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have an anniversary? Happy anniversary to you both!", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday and anniversary upcoming events happen
    @Test
    public void respondHowIsYourSpouseSheBirthDayAndAnniversaryComing(){
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Mary's birthday coming up? Please wish her a happy birthday for me. " +
                "And isn't your anniversary coming up? Happy anniversary!", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday and anniversary past events happen
    @Test
    public void respondHowIsYourSpouseSheBirthDayAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Didn't Mary just have a birthday? Please wish her a happy birthday for me. " +
                "And didn't Mary just have an anniversary? Happy anniversary to you both!", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday upcoming and anniversary past events happen
    @Test
    public void respondHowIsYourSpouseSheBirthDayComingAndAnniversaryPast() {
        int year = LocalDate.now().getYear();
        Person spouse = new Person("Mary", Sex.F, LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()+1),
                LocalDate.ofYearDay(year, LocalDate.now().getDayOfYear()-1));
        String response = impl.respondHowIsYourSpouse(spouse);
        assertEquals("Isn't Mary's birthday coming up? Please wish her a happy birthday for me." +
                " And didn't Mary just have an anniversary? Happy anniversary to you both!", response);
    }

    //Test respondHowIsYourSpouse when spouse is female and birthday past and anniversary upcoming events happen
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
