package com.ociweb;

import static com.ociweb.pronghorn.pipe.Pipe.takeRingByteLen;
import static com.ociweb.pronghorn.pipe.Pipe.takeRingByteMetaData;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ociweb.pronghorn.ExampleProducerStage;
import com.ociweb.pronghorn.pipe.*;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.route.ReplicatorStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;
import com.ociweb.pronghorn.stage.scheduling.NonThreadScheduler;
import com.ociweb.pronghorn.stage.test.PipeNoOp;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import com.ociweb.SchemaOneSchema;
import com.ociweb.pronghorn.pipe.util.build.FROMValidation;

import javax.xml.validation.Schema;
import java.io.IOException;

public class SchemaTest {

    /**
     * Verify that the schema XML matches with the generated class.
     * If not, the correct output will be displayed in the console for copying.
     */
	@Test
    public void messageClientNetResponseSchemaFROMTest() {
    	
        assertTrue(FROMValidation.checkSchema("/SchemaOne.xml", SchemaOneSchema.class));

    }
	
}
