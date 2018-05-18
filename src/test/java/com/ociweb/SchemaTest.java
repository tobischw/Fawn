package com.ociweb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ociweb.pronghorn.ExampleProducerStage;
import com.ociweb.pronghorn.pipe.ChannelReader;
import com.ociweb.pronghorn.pipe.ChannelWriter;
import com.ociweb.pronghorn.pipe.DataOutputBlobWriter;
import com.ociweb.pronghorn.pipe.Pipe;
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

public class SchemaTest {

    /**
     * Verify that the schema XML matches with the generated class.
     * If not, the correct output will be displayed in the console for copying.
     */
	@Test
    public void messageClientNetResponseSchemaFROMTest() {
    	
        assertTrue(FROMValidation.checkSchema("/SchemaOne.xml", SchemaOneSchema.class));

    }

    /**
     * Creates a new GraphManager and demonstrates how to write to a pipe directly using low-level API.
     * Then, verifies that content in output pipe from replicator is the same as content in input pipe.
     */
    @Test
    void checkPrepopulatedPipeData() {

        GraphManager gm = new GraphManager();

        Pipe inputPipe = SchemaOneSchema.instance.newPipe(50, 500);
        Pipe outputPipe = SchemaOneSchema.instance.newPipe(50, 500);

        //Pipe outputPipe = PipeNoOp.newInstance()

        inputPipe.initBuffers();

        int size = Pipe.addMsgIdx(inputPipe, SchemaOneSchema.MSG_SOMEOTHERMESSAGE_2);

        ChannelWriter cw = Pipe.openOutputStream(inputPipe);

        cw.writeInt(1000);
        cw.writeLong(500000000);

        ((DataOutputBlobWriter) cw).closeLowLevelField();

        Pipe.confirmLowLevelWrite(inputPipe, size);
        Pipe.publishWrites(inputPipe);

        //Do we still need a scheduler?


        ReplicatorStage.newInstance(gm, inputPipe, outputPipe);

        //NOW, read what's written onto the outputPipe!



        gm.enableTelemetry(7779);

    }
	
}
