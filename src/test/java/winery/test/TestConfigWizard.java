package winery.test;

import java.util.concurrent.Semaphore;

import winery.config.ConfigWizardFrame;

public class TestConfigWizard {

	public static void main(String[] args) {
		ConfigWizardFrame.loadConfig(new Semaphore(0));
	}

}
