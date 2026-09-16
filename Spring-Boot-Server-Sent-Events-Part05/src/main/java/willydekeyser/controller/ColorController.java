package willydekeyser.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ColorController {

	private final ExecutorService executor = Executors.newCachedThreadPool();
	
	@GetMapping("/color")
	public SseEmitter time() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		executor.execute(() -> {
            try {
            	Thread.sleep(5000);
            	for (int i = 0; i != 301; i++) {
            		emitter.send(SseEmitter.event()
                		.name("red")
                		.data("" + i)
                		.build());
            		Thread.sleep(10);
            	}
            	for (int i = 0; i != 301; i++) {
            		emitter.send(SseEmitter.event()
            			.name("green")
            			.data("" + i)
            			.build());
                	Thread.sleep(10);
            	}
                for (int i = 0; i != 301; i++) {
                	emitter.send(SseEmitter.event()
            			.name("blue")
            			.data("" + i)
            			.build());
                	Thread.sleep(10);
                }
                Thread.sleep(1000);
	            emitter.send(SseEmitter.event()
            			.name("valve_red")
            			.data("open")
            			.build());
	            for (int i = 300; i != -1; i--) {
                	emitter.send(SseEmitter.event()
                			.name("red")
                			.data("" + i)
                			.build());
                	emitter.send(SseEmitter.event()
	            			.name("mixer_red")
		            		.data("" + 255)
		            		.build());
                	emitter.send(SseEmitter.event()
	            			.name("mixer")
	            			.data("" + (300 - i)/3)
	            			.build());
                	Thread.sleep(10);
				}
	            emitter.send(SseEmitter.event()
            			.name("valve_green")
            			.data("open")
            			.build());
	            for (int i = 300; i != -1; i--) {
	            	Integer x = 255 - i * 255 / 300;
	            	emitter.send(SseEmitter.event()
	            			.name("green")
	            			.data("" + i)
	            			.build());
	            	emitter.send(SseEmitter.event()
		        			.name("mixer_green")
		        			.data("" + x)
		        			.build());
	            	emitter.send(SseEmitter.event()
	            			.name("mixer")
	            			.data("" + (100 + (300 - i)/3))
	            			.build());
	            	Thread.sleep(10);
				}
	            emitter.send(SseEmitter.event()
            			.name("valve_blue")
            			.data("open")
            			.build());
	            for (int i = 300; i != -1; i--) {
	            	Integer x = 255 - i * 255 / 300;
	            	emitter.send(SseEmitter.event()
	            			.name("blue")
	            			.data("" + i)
	            			.build());
	            	emitter.send(SseEmitter.event()
			    			.name("mixer_blue")
			    			.data("" + x)
			    			.build());
	            	emitter.send(SseEmitter.event()
	            			.name("mixer")
	            			.data("" + (200 + (300 - i)/3))
	            			.build());
	            	Thread.sleep(10);
	            }
	            emitter.send(SseEmitter.event()
            			.name("valve_red")
            			.data("close")
            			.build());
	            emitter.send(SseEmitter.event()
            			.name("valve_green")
            			.data("close")
            			.build());
	            emitter.send(SseEmitter.event()
            			.name("valve_blue")
            			.data("close")
            			.build());
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            } finally {
				emitter.complete();
			}
		});  
		return emitter;
	}
}
