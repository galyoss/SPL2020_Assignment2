package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		//TODO leia needs to initialize Ewoks
		// json parsing
		Ewoks ewoks = new Ewoks(8); //TODO json.ewoks.size()
		Thread leia = new Thread(new LeiaMicroservice(new Attack[2]));//TODO json.attacks
		Thread hansolo = new Thread(new HanSoloMicroservice());
		Thread C3PO = new Thread(new C3POMicroservice());
		Thread R2 = new Thread(new R2D2Microservice(100)); //TODO json.R2.Duration
		Thread lando = new Thread(new LandoMicroservice(100)); //TODO json.lando.duration


		hansolo.run();
		C3PO.run();
		R2.run();
		lando.run();
		leia.run();// TODO make sure leia w8s b4 whe starts sending events

		try {
			hansolo.join();
			C3PO.join();
			R2.join();
			lando.join();
			leia.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//now when reach here, all threads done
		//TODO: output diary to json somehow


	}
}
