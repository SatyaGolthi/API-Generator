package com.cg.osce.apibuilder.service;

import java.io.InputStream;
import java.io.OutputStream;

import com.cg.osce.apibuilder.exception.APIBuilderException;

class ErrorPipe implements Runnable
{
	public ErrorPipe(InputStream istrm, OutputStream ostrm) {
      istrm_ = istrm;
      ostrm_ = ostrm;
  }
  public void run(){
      try
      {
          final byte[] buffer = new byte[1024];
          for (int length = 0; (length = istrm_.read(buffer)) != -1; )
          {
              ostrm_.write(buffer, 0, length);
              throw new APIBuilderException(124, "Failed processing the schema");
          }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }
  private final OutputStream ostrm_;
  private final InputStream istrm_;
}