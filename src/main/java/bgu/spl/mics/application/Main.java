package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.CountDownLatch;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.TreeMap;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {

		System.out.println("start run");



		Gson gson = new Gson();
		String inputPath = args[0];
		String outputPath = args[1];
		jsonInput js = jsonInput.getInstance();
		try {
			js = gson.fromJson(new FileReader(inputPath),jsonInput.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		jsonInput test = jsonInput.getInstance();









		Thread leia = new Thread(new LeiaMicroservice(js.getAttacks()), "Leia");
		Thread hansolo = new Thread(new HanSoloMicroservice(), "Hansolo");
		Thread C3PO = new Thread(new C3POMicroservice(), "C3PO");
		Thread R2 = new Thread(new R2D2Microservice(js.getR2D2()),"R2");
		Thread lando = new Thread(new LandoMicroservice(js.getLando()), "Lando");


		CountDownLatch leiacounter = new CountDownLatch(4);
		hansolo.start();
		C3PO.start();
		R2.start();
		lando.start();
		synchronized (CountDownLatch.getInstance().getAtomic()) {


			while (leiacounter.getValue() != 0) {
				try {
					leiacounter.getAtomic().wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		leia.start();
		System.out.println("never get here");



		try {
			System.out.println("main join");
			hansolo.join();
			C3PO.join();
			R2.join();
			lando.join();
			leia.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String json = gson.toJson(Diary.getDiary());

		System.out.println("run everything");

		try (PrintWriter out = new PrintWriter(outputPath)) {
			out.println(json);
		} catch (FileNotFoundException e) {}

	}
}
