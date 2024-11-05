/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.cupidmeet.notificationservice.dto;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Message extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 836126278861055344L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Message\",\"namespace\":\"com.cupidmeet.notificationservice.dto\",\"fields\":[{\"name\":\"senderName\",\"type\":\"string\"},{\"name\":\"senderProfileUrl\",\"type\":\"string\"},{\"name\":\"receiverName\",\"type\":\"string\"},{\"name\":\"receiverEmail\",\"type\":\"string\"},{\"name\":\"message\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Message> ENCODER =
      new BinaryMessageEncoder<Message>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Message> DECODER =
      new BinaryMessageDecoder<Message>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Message> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Message> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Message>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Message to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Message from a ByteBuffer. */
  public static Message fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence senderName;
  @Deprecated public java.lang.CharSequence senderProfileUrl;
  @Deprecated public java.lang.CharSequence receiverName;
  @Deprecated public java.lang.CharSequence receiverEmail;
  @Deprecated public java.lang.CharSequence message;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Message() {}

  /**
   * All-args constructor.
   * @param senderName The new value for senderName
   * @param senderProfileUrl The new value for senderProfileUrl
   * @param receiverName The new value for receiverName
   * @param receiverEmail The new value for receiverEmail
   * @param message The new value for message
   */
  public Message(java.lang.CharSequence senderName, java.lang.CharSequence senderProfileUrl, java.lang.CharSequence receiverName, java.lang.CharSequence receiverEmail, java.lang.CharSequence message) {
    this.senderName = senderName;
    this.senderProfileUrl = senderProfileUrl;
    this.receiverName = receiverName;
    this.receiverEmail = receiverEmail;
    this.message = message;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return senderName;
    case 1: return senderProfileUrl;
    case 2: return receiverName;
    case 3: return receiverEmail;
    case 4: return message;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: senderName = (java.lang.CharSequence)value$; break;
    case 1: senderProfileUrl = (java.lang.CharSequence)value$; break;
    case 2: receiverName = (java.lang.CharSequence)value$; break;
    case 3: receiverEmail = (java.lang.CharSequence)value$; break;
    case 4: message = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'senderName' field.
   * @return The value of the 'senderName' field.
   */
  public java.lang.CharSequence getSenderName() {
    return senderName;
  }

  /**
   * Sets the value of the 'senderName' field.
   * @param value the value to set.
   */
  public void setSenderName(java.lang.CharSequence value) {
    this.senderName = value;
  }

  /**
   * Gets the value of the 'senderProfileUrl' field.
   * @return The value of the 'senderProfileUrl' field.
   */
  public java.lang.CharSequence getSenderProfileUrl() {
    return senderProfileUrl;
  }

  /**
   * Sets the value of the 'senderProfileUrl' field.
   * @param value the value to set.
   */
  public void setSenderProfileUrl(java.lang.CharSequence value) {
    this.senderProfileUrl = value;
  }

  /**
   * Gets the value of the 'receiverName' field.
   * @return The value of the 'receiverName' field.
   */
  public java.lang.CharSequence getReceiverName() {
    return receiverName;
  }

  /**
   * Sets the value of the 'receiverName' field.
   * @param value the value to set.
   */
  public void setReceiverName(java.lang.CharSequence value) {
    this.receiverName = value;
  }

  /**
   * Gets the value of the 'receiverEmail' field.
   * @return The value of the 'receiverEmail' field.
   */
  public java.lang.CharSequence getReceiverEmail() {
    return receiverEmail;
  }

  /**
   * Sets the value of the 'receiverEmail' field.
   * @param value the value to set.
   */
  public void setReceiverEmail(java.lang.CharSequence value) {
    this.receiverEmail = value;
  }

  /**
   * Gets the value of the 'message' field.
   * @return The value of the 'message' field.
   */
  public java.lang.CharSequence getMessage() {
    return message;
  }

  /**
   * Sets the value of the 'message' field.
   * @param value the value to set.
   */
  public void setMessage(java.lang.CharSequence value) {
    this.message = value;
  }

  /**
   * Creates a new Message RecordBuilder.
   * @return A new Message RecordBuilder
   */
  public static com.cupidmeet.notificationservice.dto.Message.Builder newBuilder() {
    return new com.cupidmeet.notificationservice.dto.Message.Builder();
  }

  /**
   * Creates a new Message RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Message RecordBuilder
   */
  public static com.cupidmeet.notificationservice.dto.Message.Builder newBuilder(com.cupidmeet.notificationservice.dto.Message.Builder other) {
    return new com.cupidmeet.notificationservice.dto.Message.Builder(other);
  }

  /**
   * Creates a new Message RecordBuilder by copying an existing Message instance.
   * @param other The existing instance to copy.
   * @return A new Message RecordBuilder
   */
  public static com.cupidmeet.notificationservice.dto.Message.Builder newBuilder(com.cupidmeet.notificationservice.dto.Message other) {
    return new com.cupidmeet.notificationservice.dto.Message.Builder(other);
  }

  /**
   * RecordBuilder for Message instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Message>
    implements org.apache.avro.data.RecordBuilder<Message> {

    private java.lang.CharSequence senderName;
    private java.lang.CharSequence senderProfileUrl;
    private java.lang.CharSequence receiverName;
    private java.lang.CharSequence receiverEmail;
    private java.lang.CharSequence message;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.cupidmeet.notificationservice.dto.Message.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.senderName)) {
        this.senderName = data().deepCopy(fields()[0].schema(), other.senderName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.senderProfileUrl)) {
        this.senderProfileUrl = data().deepCopy(fields()[1].schema(), other.senderProfileUrl);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.receiverName)) {
        this.receiverName = data().deepCopy(fields()[2].schema(), other.receiverName);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.receiverEmail)) {
        this.receiverEmail = data().deepCopy(fields()[3].schema(), other.receiverEmail);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.message)) {
        this.message = data().deepCopy(fields()[4].schema(), other.message);
        fieldSetFlags()[4] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Message instance
     * @param other The existing instance to copy.
     */
    private Builder(com.cupidmeet.notificationservice.dto.Message other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.senderName)) {
        this.senderName = data().deepCopy(fields()[0].schema(), other.senderName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.senderProfileUrl)) {
        this.senderProfileUrl = data().deepCopy(fields()[1].schema(), other.senderProfileUrl);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.receiverName)) {
        this.receiverName = data().deepCopy(fields()[2].schema(), other.receiverName);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.receiverEmail)) {
        this.receiverEmail = data().deepCopy(fields()[3].schema(), other.receiverEmail);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.message)) {
        this.message = data().deepCopy(fields()[4].schema(), other.message);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'senderName' field.
      * @return The value.
      */
    public java.lang.CharSequence getSenderName() {
      return senderName;
    }

    /**
      * Sets the value of the 'senderName' field.
      * @param value The value of 'senderName'.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder setSenderName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.senderName = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'senderName' field has been set.
      * @return True if the 'senderName' field has been set, false otherwise.
      */
    public boolean hasSenderName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'senderName' field.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder clearSenderName() {
      senderName = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'senderProfileUrl' field.
      * @return The value.
      */
    public java.lang.CharSequence getSenderProfileUrl() {
      return senderProfileUrl;
    }

    /**
      * Sets the value of the 'senderProfileUrl' field.
      * @param value The value of 'senderProfileUrl'.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder setSenderProfileUrl(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.senderProfileUrl = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'senderProfileUrl' field has been set.
      * @return True if the 'senderProfileUrl' field has been set, false otherwise.
      */
    public boolean hasSenderProfileUrl() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'senderProfileUrl' field.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder clearSenderProfileUrl() {
      senderProfileUrl = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'receiverName' field.
      * @return The value.
      */
    public java.lang.CharSequence getReceiverName() {
      return receiverName;
    }

    /**
      * Sets the value of the 'receiverName' field.
      * @param value The value of 'receiverName'.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder setReceiverName(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.receiverName = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'receiverName' field has been set.
      * @return True if the 'receiverName' field has been set, false otherwise.
      */
    public boolean hasReceiverName() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'receiverName' field.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder clearReceiverName() {
      receiverName = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'receiverEmail' field.
      * @return The value.
      */
    public java.lang.CharSequence getReceiverEmail() {
      return receiverEmail;
    }

    /**
      * Sets the value of the 'receiverEmail' field.
      * @param value The value of 'receiverEmail'.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder setReceiverEmail(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.receiverEmail = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'receiverEmail' field has been set.
      * @return True if the 'receiverEmail' field has been set, false otherwise.
      */
    public boolean hasReceiverEmail() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'receiverEmail' field.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder clearReceiverEmail() {
      receiverEmail = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'message' field.
      * @return The value.
      */
    public java.lang.CharSequence getMessage() {
      return message;
    }

    /**
      * Sets the value of the 'message' field.
      * @param value The value of 'message'.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder setMessage(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.message = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'message' field has been set.
      * @return True if the 'message' field has been set, false otherwise.
      */
    public boolean hasMessage() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'message' field.
      * @return This builder.
      */
    public com.cupidmeet.notificationservice.dto.Message.Builder clearMessage() {
      message = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message build() {
      try {
        Message record = new Message();
        record.senderName = fieldSetFlags()[0] ? this.senderName : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.senderProfileUrl = fieldSetFlags()[1] ? this.senderProfileUrl : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.receiverName = fieldSetFlags()[2] ? this.receiverName : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.receiverEmail = fieldSetFlags()[3] ? this.receiverEmail : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.message = fieldSetFlags()[4] ? this.message : (java.lang.CharSequence) defaultValue(fields()[4]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Message>
    WRITER$ = (org.apache.avro.io.DatumWriter<Message>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Message>
    READER$ = (org.apache.avro.io.DatumReader<Message>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
