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
        switch (feeling){
            case FINE: return "I'm glad to hear that";
            case TIRED: return "Maybe you should take a vacation";
            default: return "I'm not familiar with that condition. Is it serious?";
        }
    }

    public String respondToHowIsTheWeather(int temp, SkyCondition skyCondition){
        return "";
    }

    public String respondHowIsYourSpouse(Person person){
        return "";
    }
}
