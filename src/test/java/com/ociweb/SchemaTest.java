package com.ociweb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ociweb.pronghorn.ExampleProducerStage;
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

    @Test
    void checkPrepopulatedPipeData() {

        GraphManager gm = new GraphManager();

        Pipe pipe = SchemaOneSchema.instance.newPipe(50, 500);

        pipe.initBuffers();

        int size = Pipe.addMsgIdx(pipe, SchemaOneSchema.MSG_SOMEOTHERMESSAGE_2);

        ChannelWriter cw = Pipe.openOutputStream(pipe);

        cw.writeInt(1000);
        cw.writeLong(500000000);

        ((DataOutputBlobWriter) cw).closeLowLevelField();

        Pipe.confirmLowLevelWrite(pipe, size);
        Pipe.publishWrites(pipe);

        //Pipe emptyPipe = PipeNoOp.newInstance()

        ReplicatorStage.newInstance(gm, pipe, PronghornStage.NONE);

        gm.enableTelemetry(7779);

    }
	
}
