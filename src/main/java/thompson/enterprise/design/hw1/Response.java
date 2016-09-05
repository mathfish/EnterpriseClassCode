package thompson.enterprise.design.hw1;

import java.time.LocalDate;

public class Response {

    /**
     * Receives Greeting class and will return appropriate response String.
     * If Greeting enum is not HELLO or GREETING will respond with defualt message
     */
    public String respondToGreeting(Greeting greeting){
        if(greeting.equals(Greeting.HELLO)){
            return "Hi";
        } else if(greeting.equals(Greeting.GREETING)){
            return "Salutations";
        } else{
            return "Sorry, I didn't hear you";
        }
    }

    /**
     * Receives Feeling enum and will return response based upon Feeling enum. If enum is not reconized, will
     * return default message
     */
    public String respondToHowAreYou(Feeling feeling){
        switch (feeling){
            case FINE: return "I'm glad to hear that";
            case TIRED: return "Maybe you should take a vacation";
            default: return "I'm not familiar with that condition. Is it serious?";
        }
    }

    /**
     * Receives temperature int and a SkyCondition enum. Based upon both, will return response.
     * Will return a default message case is not met.
     */
    public String respondToHowIsTheWeather(int temp, SkyCondition skyCondition){
        switch(skyCondition){
            case SUNNY:
                return temp > 70 ? "It's beautiful out today - warm and sunny" :
                        "It's sunny out, but a little cooler than I'd like";
            case RAINY:
                return temp > 70 ? "It's rainy, but at least it's warm out!" :
                        "It's rainy and chilly, a good day to stay inside";
            default:
                return "Not sure about this weather";
        }
    }

    /**
     * Receives a person class. Will return responses that are gender appropriate and uses name attribute from Person.
     * Will add to response birthday or anniversary message if either occurred 14 days before or will occur with 14 days
     * of the current date. If both, response will combine messages. If neither occur, will return a default message.
     */
    public String respondHowIsYourSpouse(Person person){
        String response="";
        int dayOfYear = LocalDate.now().getDayOfYear();
        int birthDayOfYear = person.getBirthdate().getDayOfYear();
        int anninversaryDayOfYear = person.getAnniversary().getDayOfYear();
        //Check if birthday within next 14 days, including today
        boolean bDayComing = birthDayOfYear - dayOfYear >=0 && birthDayOfYear - dayOfYear <= 14;
        //Check if birthday happened in past 14 days, not including today
        boolean bDayPast = dayOfYear - birthDayOfYear > 0 && dayOfYear -birthDayOfYear <= 14;
        //Check if anniversary within next 14 days, including today
        boolean annDayComing = anninversaryDayOfYear - dayOfYear >=0 && anninversaryDayOfYear - dayOfYear <= 14;
        //Check if anniversary happened in past 14 days, not including today
        boolean annDayPast = dayOfYear - anninversaryDayOfYear > 0 && dayOfYear - anninversaryDayOfYear <= 14;

        if(bDayComing){
            response = "Isn't " + person.getFirstName() + "'s birthday coming up? Please wish " + himOrHer(person) +
                    " a happy birthday for me." ;
        } else if(bDayPast){
            response = "Didn't " + person.getFirstName() + " just have a birthday? Please wish " + himOrHer(person) +
                    " a happy birthday for me.";
        }

        if(annDayComing){
            if(bDayComing || bDayPast){
                response = response + " And isn't your anniversary coming up? Happy anniversary!";
            } else{
                response = "Isn't your anniversary coming up? Happy anniversary!";
            }
        } else if(annDayPast){
            if(bDayComing || bDayPast){
                response = response + " And didn't " + person.getFirstName() +
                        " just have an anniversary? Happy anniversary to you both!";
            } else{
                response = "Didn't " + person.getFirstName() +
                        " just have an anniversary? Happy anniversary to you both!";
            }
        } else if(response.equals("")){
            response = heOrShe(person) + " is doing well, thank you.";
        }

        return response;
    }

    private String heOrShe(Person person){
        return person.getSex().equals(Sex.M) ? "He" : "She";
    }

    private String himOrHer(Person person){
        return person.getSex().equals(Sex.M) ? "him" : "her";
    }
}
