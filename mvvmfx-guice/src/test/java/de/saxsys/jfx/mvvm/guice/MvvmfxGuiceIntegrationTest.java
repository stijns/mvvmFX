/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.jfx.mvvm.guice;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.Test;

import com.google.inject.Module;

public class MvvmfxGuiceIntegrationTest {
	
	/**
	 * We use this class as our test application class.
	 */
	public static class MyApplication extends MvvmfxGuiceApplication {
		
		/**
		 * To be able to verify that there was a valid stage available we need to persist the stage so we can verify it
		 * after the application has stopped. This needs to be static because we can't create an instance of this
		 * Application class on our own. This is done by the framework.
		 */
		public static Stage stage;
		
		public static Application.Parameters parameters;
		
		public static void main(String... args) {
			launch(args);
		}
		
		@Override
		public void startMvvmfx(Stage stage) throws Exception {
			MyApplication.stage = stage;
			
			MyApplication.parameters = getParameters();
			
			// we can't shutdown the application in the test case so we need to do it here.
			Platform.exit();
		}
		
		@Override
		public void initGuiceModules(List<Module> modules) throws Exception {
		}
	}
	
	
	/**
	 * Verify that after running the application there is a valid stage.
	 */
	@Test
	public void testApplicationWasStartedWithAStage() {
		MyApplication.main("test");
		
		assertThat(MyApplication.stage).isNotNull();
		assertThat(MyApplication.parameters).isNotNull();
		assertThat(MyApplication.parameters.getUnnamed()).contains("test");
	}
	
}
