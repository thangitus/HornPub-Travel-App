package com.ygaps.travelapp.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;

public class RecordAudio {
   private static final String LOG_TAG = "RecordAudio";
   private static String fileName = null;
   Context context;
   LottieAnimationView buttonRecord;
   boolean isRecording = false;
   private MediaRecorder recorder = null;
   private MediaPlayer player = null;

   public RecordAudio(Context context, final LottieAnimationView buttonRecord) {
      this.context = context;
      this.buttonRecord = buttonRecord;

      fileName = context.getExternalCacheDir().getAbsolutePath();
      fileName += "/audio.3gp";

      buttonRecord.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            onRecord(isRecording);
            if (!isRecording) {
               buttonRecord.playAnimation();
            } else {
               buttonRecord.setProgress(0);
               buttonRecord.cancelAnimation();
               startPlaying();
            }
            isRecording = !isRecording;
         }
      });
   }
   private void onRecord(boolean isRecording) {
      if (!isRecording) {
         startRecording();
      } else {
         stopRecording();
      }
   }

   private void startPlaying() {
      player = new MediaPlayer();
      try {
         player.setDataSource(fileName);
         player.prepare();
         player.start();
      } catch (IOException e) {
         Log.e(LOG_TAG, "prepare() failed");
      }
   }

   private void startRecording() {
      recorder = new MediaRecorder();
      recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
      recorder.setOutputFile(fileName);
      recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

      try {
         recorder.prepare();
      } catch (IOException e) {
         Log.e(LOG_TAG, "prepare() failed");
      }
      recorder.start();
   }

   private void stopRecording() {
      recorder.stop();
      recorder.release();
      recorder = null;
   }
   public void releaseRecorder() {
      if (recorder != null) {
         recorder.release();
         recorder = null;
      }

      if (player != null) {
         player.release();
         player = null;
      }
   }

}
