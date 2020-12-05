package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;

import java.awt.*;
import java.util.LinkedList;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		//TODO leia needs to initialize Ewoks
		// json parsing
		System.out.println("start run");

		Ewoks ewoks = new Ewoks(2); //TODO json.ewoks.size()
		LinkedList<Integer> l1 = new LinkedList<>();
		l1.add(1);
		l1.add(2);
		LinkedList<Integer> l2 = new LinkedList<>();
		l2.add(2);
		l2.add(1);
		Attack[] attacks = {new Attack(l1, 1000), new Attack(l2, 1000)};
		Thread leia = new Thread(new LeiaMicroservice(new Attack[2]));//TODO json.attacks
		Thread hansolo = new Thread(new HanSoloMicroservice());
		Thread C3PO = new Thread(new C3POMicroservice());
		Thread R2 = new Thread(new R2D2Microservice(2000)); //TODO json.R2.Duration
		Thread lando = new Thread(new LandoMicroservice(2000)); //TODO json.lando.duration


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
		System.out.println("run everything");

	}
}
