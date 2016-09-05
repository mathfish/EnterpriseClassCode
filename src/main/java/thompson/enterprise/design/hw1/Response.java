package thompson.enterprise.design.hw1;

public class Response {

    public String respondToGreeting(Greeting greeting){
        if(greeting.equals(Greeting.HELLO)){
            return "Hi";
        } else if(greeting.equals(Greeting.GREETING)){
            return "Salutations";
        } else{
            return "Sorry, I didn't hear you";
        }
    }

    public String respondToHowAreYou(Feeling feeling){
        return "";
    }

    public String respondToHowIsTheWeather(int temp, SkyCondition skyCondition){
        return "";
    }

    public String respondHowIsYourSpouse(Person person){
        return "";
    }
}
