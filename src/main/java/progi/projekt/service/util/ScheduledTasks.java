package progi.projekt.service.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import progi.projekt.service.MatchingService;

@Component
public class ScheduledTasks {
	private MatchingService matchingService;

	private static final boolean ENABLE_SCHEDULER = false;
	private static final int CALL_DELAY = 20 * 60 * 1000; //20 min
	private static final int MILISEC_IZMEDJU_POZIVA = 5 * 1000; //5 s

	public ScheduledTasks(MatchingService matchingService) {
		this.matchingService = matchingService;
	}

	@Scheduled(fixedRate = CALL_DELAY)
	public void runMatchingAlgorithm() {
		if (ENABLE_SCHEDULER){
			System.out.println("Running scheduled matching execution");

			final boolean THREADS = false;
			if (THREADS){
				try {
					Thread kandidatiThread = new Thread(() -> matchingService.kandidatiFun());
					kandidatiThread.start();
					Thread.sleep(MILISEC_IZMEDJU_POZIVA);

					Thread parThread = new Thread(() -> matchingService.parFun());
					parThread.start();
					Thread.sleep(MILISEC_IZMEDJU_POZIVA);

					Thread lajkThread = new Thread(() -> matchingService.lajkFun());
					lajkThread.start();
					Thread.sleep(MILISEC_IZMEDJU_POZIVA);

					Thread matchThread = new Thread(() -> matchingService.matchFun());
					matchThread.start();
					Thread.sleep(MILISEC_IZMEDJU_POZIVA);

					Thread confirmThread = new Thread(() -> matchingService.confirmFun());
					confirmThread.start();
					Thread.sleep(MILISEC_IZMEDJU_POZIVA);

					Thread confirmSCThread = new Thread(() -> matchingService.confirmSCFun());
					confirmSCThread.start();
					Thread.sleep(MILISEC_IZMEDJU_POZIVA);

				} catch (InterruptedException e) {
					System.err.println("Scheduled matching execution interrupted");
				}
			} else {
				matchingService.kandidatiFun();
				matchingService.parFun();
				matchingService.lajkFun();
				matchingService.matchFun();
				matchingService.confirmFun();
				matchingService.confirmSCFun();
			}


		}
	}
}
