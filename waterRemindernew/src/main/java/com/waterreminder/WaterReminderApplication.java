package com.waterreminder;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class WaterReminderApplication {

	private static final Dotenv dotenv = Dotenv.load();
	private static final String ACCOUNT_SID = dotenv.get("TWILIO_ACCOUNT_SID");
	private static final String AUTH_TOKEN = dotenv.get("TWILIO_AUTH_TOKEN");
	private static final String FROM_PHONE = dotenv.get("TWILIO_PHONE_NUMBER");
	private static final String TO_PHONE = dotenv.get("RECEIVER_PHONE_NUMBER");

	private static final String[] MESSAGES = {
			"💦 Hydration check! Your body just DM'd me—it’s thirsty. Sip up! 🚰",
			"👀 If you can read this, you need water. No excuses. Chug! 🚀",
			"🏆 Pro tip: Drinking water = Leveling up IRL. Stay hydrated, champ! 🥤",
			"🔥 Hydration is the secret to looking hot. Drink up, gorgeous! 😘",
			"🤠 Yeehaw! let's get yourself a bottle of water and drink up, partner! 🤠",
			"🧊 Ice, ice baby! But first… WATER! 🚰",
			"🥤 Sip sip hooray! Take a break and hydrate, legend. 🌊",
			"💪 Muscles? Check. Brain power? Check. Water? Oh wait… Go drink some! 📢",
			"🕵️‍♂️ I see you, and I see an empty water bottle. Fix it now! 🚨",
			"🚀 NASA just confirmed: Hydrated people are 50% cooler. Drink up, Queen! 🛰️",
			"Hey, hydration queen! Don’t let your insides turn into the Sahara—chug some water and keep it classy!"
	};

	static {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	}

	public static void main(String[] args) {
		SpringApplication.run(WaterReminderApplication.class, args);
	}

	@Scheduled(cron = "0 0 9,12,14,15,17,18 * * *") // Sends reminder at 9 AM, 12 PM, 3 PM, 6 PM
	public void sendWaterReminder() {
		Random random = new Random();
		String messageText = MESSAGES[random.nextInt(MESSAGES.length)];

		Message message = Message.creator(
				new PhoneNumber(TO_PHONE),
				new PhoneNumber(FROM_PHONE),
				messageText
		).create();

		System.out.println("Reminder sent: " + message.getSid());
	}
}
