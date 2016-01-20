package winery.test;

import java.util.concurrent.Semaphore;

import winery.config.ConfigWizardFrame;

public class TestConfigWizard {

	public static void main(String[] args) {
		new ConfigWizardFrame(new Semaphore(0));
	}

}
