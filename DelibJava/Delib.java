package DelibJava;

//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//
//
//
//  Delib.java
//  project: DELIB
//
//
//  (c) DEDITEC GmbH, 2009-2016
//  web: http://www.deditec.de/
//  mail: vertrieb@deditec.de
//
//
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Delib 
{
	// ----------------------------------------------------------------------------
	// Private global vars
	private int job_id = 0;
	private Socket socket;
	private InputStream input_stream;
	private OutputStream output_stream;
	private byte[] tx_buffer = new byte[256];
	private int amount_bytes_received;
	private byte[] rx_buffer = new byte[256];
	private byte[] global_ad_buffer = new byte[128*4];	// Max 128 chan
	private long dapi_last_error = 0;
	private String dapi_last_error_text;

	// ----------------------------------------------------------------------------	
	// Intern
	// ----------------------------------------------------------------------------	
	private static final long DEDITEC_TCP_START_ID_FOR_MULTIPLE_BYTE_DATA			= 35;
	// ----------------------------------------------------------------------------	
	private static final long DEDITEC_PACKET_ID_0									= 0x63;
	private static final long DEDITEC_PACKET_ID_1									= 0x9a;
	private static final long DEDITEC_TCP_ANSWER_RO_1								= 0x81;
	private static final long DEDITEC_TCP_ANSWER_OK									= 0;
	// ----------------------------------------------------------------------------	
	// RO-FIFO Commands
	private static final long RO_FIFO_CMD_fifo_init 								= 1;
	private static final long RO_FIFO_CMD_fifo_set_intervalL						= 2;
	private static final long RO_FIFO_CMD_fifo_set_intervalH						= 3;
	private static final long RO_FIFO_CMD_fifo_enable_chL							= 4;
	private static final long RO_FIFO_CMD_fifo_enable_chH							= 5;
	private static final long RO_FIFO_CMD_fifo_enable_disable						= 6;
	
	// ----------------------------------------------------------------------------
	// ERROR Codes
	public static final long DAPI_ERR_NONE											= 0;
	public static final long DAPI_ERR_DEVICE_NOT_FOUND								= -1;
	public static final long DAPI_ERR_COMMUNICATION_ERROR							= -2;
	public static final long DAPI_ERR_ILLEGAL_HANDLE								= -10;
	public static final long DAPI_ERR_FUNCTION_NOT_DEFINED							= -11;
	public static final long DAPI_ERR_ILLEGAL_COM_HANDLE							= -12;
	public static final long DAPI_ERR_ILLEGAL_MODE									= -13;
	public static final long DAPI_ERR_WITH_TEXT										= -14;
	public static final long DAPI_ERR_SW_FEATURE_NOT_SUPPORTED						= -15;
	public static final long DAPI_ERR_ILLEGAL_IO_TYPE								= -16;
	public static final long DAPI_ERR_ILLEGAL_CHANNEL								= -17;

	// ----------------------------------------------------------------------------
	// Special Function-Codes
	public static final long DAPI_SPECIAL_CMD_GET_MODULE_CONFIG						= 1;
	public static final long DAPI_SPECIAL_CMD_TIMEOUT								= 2;
	public static final long DAPI_SPECIAL_CMD_DI									= 10;
	public static final long DAPI_SPECIAL_CMD_SET_DIR_DX_1							= 3;
	public static final long DAPI_SPECIAL_CMD_SET_DIR_DX_8							= 4;
	public static final long DAPI_SPECIAL_CMD_GET_MODULE_VERSION					= 5;
	public static final long DAPI_SPECIAL_CMD_DA									= 6;
	public static final long DAPI_SPECIAL_CMD_WATCHDOG								= 7;
	public static final long DAPI_SPECIAL_CMD_COUNTER								= 8;
	public static final long DAPI_SPECIAL_CMD_AD									= 9;
	public static final long DAPI_SPECIAL_CMD_CNT48									= 11;
	public static final long DAPI_SPECIAL_CMD_SOFTWARE_FIFO							= 12;
	public static final long DAPI_SPECIAL_CMD_MODULE_REBOOT							= 13;
	public static final long DAPI_SPECIAL_CMD_MODULE_RESCAN							= 14;
	public static final long DAPI_SPECIAL_CMD_RESTART_CHECK_MODULE_CONFIG			= 15;

	// values for PAR1
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI					= 1;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI_FF				= 7;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI_COUNTER			= 8;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DO					= 2;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DX					= 3;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_AD					= 4;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DA					= 5;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_TEMP				= 9;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_STEPPER				= 6;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_CNT48				= 10;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_PULSE_GEN			= 11;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_PWM_OUT				= 12;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_HW_INTERFACE1		= 13;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_SW_FEATURE1			= 14;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_HW_GROUP			= 15;
	public static final long DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_SW_CLASS			= 16;
	//
	public static final long DAPI_SPECIAL_GET_MODULE_PAR_VERSION_0					= 0;
	public static final long DAPI_SPECIAL_GET_MODULE_PAR_VERSION_1					= 1;
	public static final long DAPI_SPECIAL_GET_MODULE_PAR_VERSION_2					= 2;
	public static final long DAPI_SPECIAL_GET_MODULE_PAR_VERSION_3					= 3;
	//
	public static final long DAPI_SPECIAL_TIMEOUT_SET_VALUE_SEC						= 1;
	public static final long DAPI_SPECIAL_TIMEOUT_ACTIVATE							= 2;
	public static final long DAPI_SPECIAL_TIMEOUT_DEACTIVATE						= 3;
	public static final long DAPI_SPECIAL_TIMEOUT_GET_STATUS						= 4;
	public static final long DAPI_SPECIAL_TIMEOUT_DO_VALUE_LOAD_DEFAULT				= 5;
	public static final long DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_WR_CLR32			= 6;
	public static final long DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_RD_CLR32			= 7;
	public static final long DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_WR_SET32			= 8;
	public static final long DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_RD_SET32			= 9;
	//
	public static final long DAPI_SPECIAL_DI_FF_FILTER_VALUE_SET					= 1;
	public static final long DAPI_SPECIAL_DI_FF_FILTER_VALUE_GET					= 2;
	//
	public static final long DAPI_SPECIAL_AD_READ_MULTIPLE_AD						= 1;
	public static final long DAPI_SPECIAL_AD_FIFO_ACTIVATE							= 2;
	public static final long DAPI_SPECIAL_AD_FIFO_DEACTIVATE						= 3;
	public static final long DAPI_SPECIAL_AD_FIFO_GET_STATUS						= 4;
	public static final long DAPI_SPECIAL_AD_FIFO_SET_INTERVAL_MS					= 5;
	public static final long DAPI_SPECIAL_AD_FIFO_SET_CHANNEL						= 6;
	public static final long DAPI_SPECIAL_AD_FIFO_INIT								= 7;
	public static final long DAPI_SPECIAL_AD_FILTER_SET								= 8;
	public static final long DAPI_SPECIAL_DA_PAR_DA_LOAD_DEFAULT					= 1;
	public static final long DAPI_SPECIAL_DA_PAR_DA_SAVE_EEPROM_CONFIG				= 2;
	public static final long DAPI_SPECIAL_DA_PAR_DA_LOAD_EEPROM_CONFIG				= 3;
	//
	public static final long DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_MSEC					= 1;
	public static final long DAPI_SPECIAL_WATCHDOG_GET_TIMEOUT_MSEC					= 2;
	public static final long DAPI_SPECIAL_WATCHDOG_GET_STATUS						= 3;
	public static final long DAPI_SPECIAL_WATCHDOG_GET_WD_COUNTER_MSEC				= 4;
	public static final long DAPI_SPECIAL_WATCHDOG_GET_TIMEOUT_RELAIS_COUNTER_MSEC	= 5;
	public static final long DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_REL1_COUNTER_MSEC	= 6;
	public static final long DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_REL2_COUNTER_MSEC	= 7;
	//
	public static final long DAPI_SPECIAL_COUNTER_LATCH_ALL							= 1;
	public static final long DAPI_SPECIAL_COUNTER_LATCH_ALL_WITH_RESET				= 2;
	//
	public static final long DAPI_SPECIAL_CNT48_RESET_SINGLE						= 1;
	public static final long DAPI_SPECIAL_CNT48_RESET_GROUP8						= 2;
	public static final long DAPI_SPECIAL_CNT48_LATCH_GROUP8						= 3;
	public static final long DAPI_SPECIAL_CNT48_DI_GET1								= 4;
	//
	public static final long DAPI_SPECIAL_SOFTWARE_FIFO_ACTIVATE					= 1;
	public static final long DAPI_SPECIAL_SOFTWARE_FIFO_DEACTIVATE					= 2;
	public static final long DAPI_SPECIAL_SOFTWARE_FIFO_GET_STATUS					= 3;

	// values for PAR2
	public static final long DAPI_SPECIAL_AD_CH0_CH15								= 0;
	public static final long DAPI_SPECIAL_AD_CH16_CH31								= 1;

	// ----------------------------------------------------------------------------
	// DapiScanModules-Codes
	public static final long DAPI_SCANMODULE_GET_MODULES_AVAILABLE					= 1;

	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// DI - Counter Mode

	public static final long DAPI_CNT_MODE_READ_WITH_RESET							= 0x01; 
	public static final long DAPI_CNT_MODE_READ_LATCHED								= 0x02;
	public static final long DAPI_SW_CLASS_AD_DA_CHANNELS							= 0x04;

	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// A/D and D/A Modes
	
	public static final long ADDA_MODE_UNIPOL_10V 									= 0x00;
	public static final long ADDA_MODE_UNIPOL_5V 									= 0x01;
	public static final long ADDA_MODE_UNIPOL_2V5 									= 0x02;
	
	public static final long ADDA_MODE_BIPOL_10V 									= 0x40;
	public static final long ADDA_MODE_BIPOL_5V 									= 0x41;
	public static final long ADDA_MODE_BIPOL_2V5 									= 0x42;
	
	public static final long ADDA_MODE_0_20mA 										= 0x80;
	public static final long ADDA_MODE_4_20mA										= 0x81;
	public static final long ADDA_MODE_0_24mA 										= 0x82;

	public static final long ADDA_MODE_DA_DISABLE 									= 0x100;
	public static final long ADDA_MODE_DA_ENABLE 									= 0x200;
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// Stepper-Defines

	// ------------------------------------
	// ERROR Codes
	public static final long DAPI_STEPPER_ERR_NONE									= 0;		// es liegt kein Fehler vor 
	public static final long DAPI_STEPPER_ERR_PARAMETER								= 1;		// Parameter hat falschen Wertebereich 
	public static final long DAPI_STEPPER_ERR_MOTOR_MOVE							= 2;		// Kommando abgelehnt, da sich der Motor dreht
	public static final long DAPI_STEPPER_ERR_DISABLE_MOVE							= 3;		// Kommando abgehelnt, da Motorbewegung disabled ist
	public static final long DAPI_STEPPER_ERR_DEVICE_NOT_FOUND						= -1;		// es liegt kein Fehler vor 

	// ------------------------------------
	// Special Stepper Function-Codes
	public static final long DAPI_STEPPER_RETURN_0_BYTES 							= 0x00000000;		// Kommando schickt 0 Byte als Antwort
	public static final long DAPI_STEPPER_RETURN_1_BYTES 							= 0x40000000;		// Kommando schickt 1 Byte als Antwort
	public static final long DAPI_STEPPER_RETURN_2_BYTES 							= 0x80000000;		// Kommando schickt 2 Byte als Antwort
	public static final long DAPI_STEPPER_RETURN_4_BYTES 							= 0xc0000000;		// Kommando schickt 4 Byte als Antwort

	public static final long DAPI_STEPPER_CMD_SET_MOTORCHARACTERISTIC               = ( 0x00000001 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_GET_MOTORCHARACTERISTIC               = ( 0x00000002 + DAPI_STEPPER_RETURN_4_BYTES ); 
	public static final long DAPI_STEPPER_CMD_SET_POSITION                          = ( 0x00000003 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_GO_POSITION                           = ( 0x00000004 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_GET_POSITION                          = ( 0x00000005 + DAPI_STEPPER_RETURN_4_BYTES );  
	public static final long DAPI_STEPPER_CMD_SET_FREQUENCY                         = ( 0x00000006 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_SET_FREQUENCY_DIRECTLY                = ( 0x00000007 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_GET_FREQUENCY                         = ( 0x00000008 + DAPI_STEPPER_RETURN_2_BYTES );  
	public static final long DAPI_STEPPER_CMD_FULLSTOP                              = ( 0x00000009 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_STOP                                  = ( 0x00000010 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_GO_REFSWITCH                          = ( 0x00000011 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_DISABLE                               = ( 0x00000014 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_MOTORCHARACTERISTIC_LOAD_DEFAULT		= ( 0x00000015 + DAPI_STEPPER_RETURN_0_BYTES );
	public static final long DAPI_STEPPER_CMD_MOTORCHARACTERISTIC_EEPROM_SAVE		= ( 0x00000016 + DAPI_STEPPER_RETURN_0_BYTES );
	public static final long DAPI_STEPPER_CMD_MOTORCHARACTERISTIC_EEPROM_LOAD		= ( 0x00000017 + DAPI_STEPPER_RETURN_0_BYTES );
	public static final long DAPI_STEPPER_CMD_GET_CPU_TEMP							= ( 0x00000018 + DAPI_STEPPER_RETURN_1_BYTES );
	public static final long DAPI_STEPPER_CMD_GET_MOTOR_SUPPLY_VOLTAGE				= ( 0x00000019 + DAPI_STEPPER_RETURN_2_BYTES );
	public static final long DAPI_STEPPER_CMD_GO_POSITION_RELATIVE                  = ( 0x00000020 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_EEPROM_ERASE							= ( 0x00000021 + DAPI_STEPPER_RETURN_0_BYTES );  
	public static final long DAPI_STEPPER_CMD_SET_VECTORMODE                        = ( 0x00000040 + DAPI_STEPPER_RETURN_0_BYTES );
	public static final long DAPI_STEPPER_CMD_GET_STATUS                            = ( 0x00000015 + DAPI_STEPPER_RETURN_1_BYTES );

	// ------------------------------------
	// values for PAR1 for DAPI_STEPPER_CMD_SET_MOTORCHARACTERISTIC

	public static final long DAPI_STEPPER_MOTORCHAR_PAR_STEPMODE					= 1;					// Schrittmode (Voll-, Halb-, Viertel-, Achtel-, Sechszehntelschritt 
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_GOFREQUENCY					= 2;					// Schrittfrequenz bei GoPosition [Vollschritt / s]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_STARTFREQUENCY				= 3;					// Startfrequenz [Vollschritt / s]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_STOPFREQUENCY				= 4;					// Stopfrequenz [Vollschritt / s]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_MAXFREQUENCY				= 5;					// maximale Frequenz [Vollschritt / s]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_ACCELERATIONSLOPE			= 6;					// Beschleunigung in [Vollschritten / ms]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_DECELERATIONSLOPE			= 7;					// Bremsung in [Vollschritten / ms]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_PHASECURRENT				= 8;					// Phasenstrom [mA]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_HOLDPHASECURRENT			= 9;					// Phasenstrom bei Motorstillstand [mA]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_HOLDTIME 					= 10;					// Zeit in der der Haltestrom flie�t nach Motorstop [s]
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_STATUSLEDMODE				= 11;					// Betriebsart der Status-LED
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_INVERT_ENDSW1				= 12;					// invertiere Funktion des Endschalter1  
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_INVERT_ENDSW2				= 13;					// invertiere Funktion des Endschalter12 
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_INVERT_REFSW1				= 14;					// invertiere Funktion des Referenzschalterschalter1
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_INVERT_REFSW2				= 15;					// invertiere Funktion des Referenzschalterschalter2
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_INVERT_DIRECTION 			= 16;					// invertiere alle Richtungsangaben
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_ENDSWITCH_STOPMODE			= 17;					// Bei Endschalter soll (0=full stop/1=stop mit rampe)
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_GOREFERENCEFREQUENCY_TOENDSWITCH	= 18;			// Motor Frequency for GoReferenceCommand
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_GOREFERENCEFREQUENCY_AFTERENDSWITCH	= 19;			// Motor Frequency for GoReferenceCommand
	public static final long DAPI_STEPPER_MOTORCHAR_PAR_GOREFERENCEFREQUENCY_TOOFFSET 		= 20;			// Motor Frequency for GoReferenceCommand

	// ----------------------------------------------------------------------------
	// values for PAR1 for DAPI_STEPPER_CMD_GO_REFSWITCH

	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_REF1						= 1;
	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_REF2						= 2;
	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_REF_LEFT					= 4;
	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_REF_RIGHT				= 8;
	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_REF_GO_POSITIVE			= 16;
	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_REF_GO_NEGATIVE			= 32;
	public static final long DAPI_STEPPER_GO_REFSWITCH_PAR_SET_POS_0				= 64;

	// ------------------------------------
	// Stepper Read Status
	
	public static final long DAPI_STEPPER_STATUS_GET_POSITION						= 0x01;
	public static final long DAPI_STEPPER_STATUS_GET_SWITCH							= 0x02;
	public static final long DAPI_STEPPER_STATUS_GET_ACTIVITY						= 0x03;
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// CNT48 Commands

	public static final long DAPI_CNT48_FILTER_20ns									= 0x0000;
	public static final long DAPI_CNT48_FILTER_100ns								= 0x1000;
	public static final long DAPI_CNT48_FILTER_250ns								= 0x2000;
	public static final long DAPI_CNT48_FILTER_500ns								= 0x3000;
	public static final long DAPI_CNT48_FILTER_1us									= 0x4000;
	public static final long DAPI_CNT48_FILTER_2_5us								= 0x5000;
	public static final long DAPI_CNT48_FILTER_5us									= 0x6000;
	public static final long DAPI_CNT48_FILTER_10us									= 0x7000;
	public static final long DAPI_CNT48_FILTER_25us									= 0x8000;
	public static final long DAPI_CNT48_FILTER_50us									= 0x9000;
	public static final long DAPI_CNT48_FILTER_100us								= 0xA000;
	public static final long DAPI_CNT48_FILTER_250us								= 0xB000;
	public static final long DAPI_CNT48_FILTER_500us								= 0xC000;
	public static final long DAPI_CNT48_FILTER_1ms									= 0xD000;
	public static final long DAPI_CNT48_FILTER_2_5ms								= 0xE000;
	public static final long DAPI_CNT48_FILTER_5ms									= 0xF000;

	public static final long DAPI_CNT48_MODE_COUNT_RISING_EDGE						= 0x0000;
	public static final long DAPI_CNT48_MODE_T										= 0x000D;
	public static final long DAPI_CNT48_MODE_FREQUENCY								= 0x000E;
	public static final long DAPI_CNT48_MODE_PWM									= 0x000F;

	public static final long DAPI_CNT48_SUBMODE_NO_RESET							= 0x0000;
	public static final long DAPI_CNT48_SUBMODE_RESET_WITH_READ 					= 0x0010;
	public static final long DAPI_CNT48_SUBMODE_RESET_ON_CH_7 						= 0x0070;
	public static final long DAPI_CNT48_SUBMODE_LATCH_COMMON 						= 0x0080;

	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// DELIB-Functions
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// Management
	
	public long DapiOpenModuleEx(String ipAdresseDeditec, long portNoDeditec, long timeout_ms) throws Exception
	{
		try 
		{	
			// set input_socket settings
			socket = new Socket();
			socket.connect(new InetSocketAddress(ipAdresseDeditec, (int) portNoDeditec), (int) timeout_ms);
			
		    // set input/output stream to SOCKETcket
		    input_stream = socket.getInputStream();
		    output_stream = socket.getOutputStream();
		}
		finally {}
		
		long handle = 0x1234;
		return handle;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiCloseModule() throws Exception
	{
		try 
		{
			socket.close();
		}
		finally {}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// Register Access
	
	
	public void DapiDOSet1_WithTimer(long handle, long ch, long data, long time_ms)
	{
		DapiWriteLong(handle, 0x200, time_ms);
		DapiWriteWord(handle, 0x204, (ch << 8) | data);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDOSet1(long handle, long ch, long data)
	{
		long xx;
		long mask;
	
		xx = DapiDOReadback32(handle, ch&0xffe0);
	
		mask = 1 << (ch&31);
	
		if(data == 0)
		{
			// clear Bit
			xx = xx & (~mask);
		}
		else
		{
			// set Bit
			xx = xx | mask;
		}
	
		DapiDOSet32(handle, ch&0xffe0, xx);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDOSet8(long handle, long ch, long data)
	{					
		DapiWriteByte(handle, (ch>>3) & 0x1f, data);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDOSet16(long handle, long ch, long data)
	{
		DapiWriteWord(handle, (ch>>3) & (~1) & 0x1f, data);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDOSet32(long handle, long ch, long data)
	{
		DapiWriteLong(handle, (ch>>3) & (~3) & 0x1f, data);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDIGet1(long handle, long ch)
	{
		long xx;
		long mask;
	
		xx = DapiDIGet8(handle, ch&0xfff8);
	
		mask = 1 << (ch&7);
		if((xx&mask) == 0)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDIGet8(long handle, long ch)
	{
		long ret;
	
		ret = DapiReadByte(handle, 0x20 | ((ch>>3) & 0x1f));
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDIGet16(long handle, long ch)
	{
		long ret;
	
		ret = DapiReadWord(handle, 0x20 | ((ch>>3) & 0x1f));
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDIGet32(long handle, long ch)
	{
		long ret;
	
		ret = DapiReadLong(handle, 0x20 | ((ch>>3) & 0x1f));
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDIGetFF32(long handle, long ch)
	{
		long ret;
	
		ret = DapiReadLong(handle, 0x40 | ((ch>>3) & 0x1f));
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDIGetCounter(long handle, long ch, long mode)
	{
		long adr;
		long ret;
	
		adr = 0x100 + (ch&0x1f)*2;
		if(mode == DAPI_CNT_MODE_READ_WITH_RESET)
		{
			adr+=0x100;
		}
		else if(mode == DAPI_CNT_MODE_READ_LATCHED)
		{
			adr+=0x200;
		}
	
		ret = DapiReadWord(handle, adr);
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDOReadback32(long handle, long ch)
	{
		long ret;
	
		ret = DapiReadLong(handle, (ch>>3) & 0x1f);
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiADSetMode(long handle, long ch, long mode)
	{
		DapiWriteByte(handle, 0x1000 + ch*4 + 3, mode);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiADGetMode(long handle, long ch)
	{
		return DapiReadByte(handle, 0x1000 + ch*4 + 3);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiADGet(long handle, long ch)
	{
		long ret;
	
		if ((ch & 0x8000) == 0)
		{
			return DapiReadWord(handle, 0x1000 + ch*4);
		}
		else
		{
			ret  = ((long) global_ad_buffer[(int) ((ch&63) * 4) + 0]);
			ret |= ((long) global_ad_buffer[(int) ((ch&63) * 4) + 1]) << 8;
			return ret;
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public float DapiADGetVolt(long handle, long ch)
	{
		long data;
		long mode;
		float value=0;
	
		if ((ch & 0x8000) == 0)
		{
			data = DapiReadLong(handle, 0x1000 + ch*4);
		}
		else
		{
			data = ((long) global_ad_buffer[(int) ((ch&127) * 4) + 0]);
			data|= ((long) global_ad_buffer[(int) ((ch&127) * 4) + 1]) << 8;
			data|= ((long) global_ad_buffer[(int) ((ch&127) * 4) + 2]) << 16;
			data|= ((long) global_ad_buffer[(int) ((ch&127) * 4) + 3]) << 24;
		}
	
		mode = (data >> 24) & 0xff;
	
		switch((int) mode)
		{
			case (int) ADDA_MODE_UNIPOL_10V:
				// 0-10V
				value = (((float) (data&0xffff)) *10.0F / 65536.0F);
				break;
	
			case (int) ADDA_MODE_UNIPOL_5V:
				// 0-5V
				value = (((float) (data&0xffff)) *5.0F / 65536.0F);
				break;
	
			case (int) ADDA_MODE_BIPOL_10V:
				// +-10V
				value = (((float) (data&0xffff)) *20.0F / 65536.0F) - 10.0F;
				break;
	
			case (int) ADDA_MODE_BIPOL_5V:
				// +-5V
				value = (((float) (data&0xffff)) *10.0F / 65536.0F) - 5.0F;
				break;
	
			default:					
				break;
		}
		return value;
	}
	
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public float DapiADGetmA(long handle, long ch)
	{
		long data;
		long mode;
		float value=0;
	
		if ((ch & 0x8000) == 0)
		{
			data = DapiReadLong(handle, 0x1000 + ch*4);
		}
		else
		{
			data = global_ad_buffer[(int) ch&63];
		}
	
	
		mode = data >> 24;
	
		switch((int) mode)
		{
			case (int) ADDA_MODE_0_24mA:
			case (int) ADDA_MODE_0_20mA:
			case (int) ADDA_MODE_4_20mA:
				// 0-5V entspricht 0-25mA (100 Ohm) und Spannungsverdopplung !
				value = (((float) (data&0xffff)) *25.0F / 65536.0F);
				break;
	
			default:			
				break;
	
		}
	
		
		return value;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDASetMode(long handle, long ch, long mode)
	{
		DapiWriteByte(handle, 0x2000 + ch*8 + 2, mode&255);
	
		if((mode & ADDA_MODE_DA_DISABLE) == ADDA_MODE_DA_DISABLE)
		{
			DapiWriteByte(handle, 0x2000 + ch*8 + 3, 1);	// Disable D/A Channel
		}
		if((mode & ADDA_MODE_DA_ENABLE) == ADDA_MODE_DA_ENABLE)
		{
			DapiWriteByte(handle, 0x2000 + ch*8 + 3, 0);	// Enable D/A Channel
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiDAGetMode(long handle, long ch)
	{
		return DapiReadByte(handle, 0x2000 + ch*8 + 2);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDASet(long handle, long ch, long data)
	{
		DapiWriteWord(handle, 0x2000 + ch*8, data);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDASetVolt(long handle, long ch, float volt)
	{
		long mode;
		long value;
		mode = 	DapiDAGetMode(handle, ch);
	
		switch((int) mode)
		{
			case (int) ADDA_MODE_UNIPOL_10V:
				value = (long) (3276.8 *2.0 * volt);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
	
			case (int) ADDA_MODE_UNIPOL_5V:
				value = (long) (3276.8 * 4.0 * volt);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
			
			case (int) ADDA_MODE_UNIPOL_2V5:
				value = (long) (3276.8 * 8.0 * volt);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
			
			case (int) ADDA_MODE_BIPOL_10V:
				value = (long) (32768.0 + 3276.8 * volt);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
			
			case (int) ADDA_MODE_BIPOL_5V:
				value = (long) (32768.0 + 3276.8 * 2.0 * volt);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
	
			case (int) ADDA_MODE_BIPOL_2V5:
				value = (long) (32768.0 + 3276.8 * 4.0 * volt);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
	
			default:	
				break;
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiDASetmA(long handle, long ch, float data)
	{
		long mode;
		long value;
	
		mode = 	DapiDAGetMode(handle, ch);
	
		switch((int) mode)
		{
			case (int) ADDA_MODE_0_20mA:
				if(data<0) data=0;
				value = (long) (3276.8 * data);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
	
			case (int) ADDA_MODE_4_20mA:
				if(data<4) data=4;
				value = (long) (4096 * (data-4.0));
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
			
			case (int) ADDA_MODE_0_24mA:
				if(data<4) data=4;
				value = (long) (65536/24 * data);
				if(value > 0xffff) value = 0xffff;
				DapiDASet(handle, ch, value );
				break;
	
			default:			
				break;
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public float DapiTempGet(long handle, long ch)
	{
		float f;
		long d;
		
		if((ch&0x8000) == 0)
		{
			// Temperatur soll gelesen werden
			d = DapiReadLong(handle, 0x4000 + ch*8);
	
			switch((int) (d>>16) & 0xff)
			{
				case 1:	f = ((float) (d&0x7fff)) / 10;break;				// Faktor 10
				case 2:	f = ((float) (d&0x7fff)) / 100;break;				// Faktor 100
				case 0:	f = -9999;break;									// Faktor Sensor disconnected
				default: f=0;
				
			}
	
			if(((d>>15)&1) != 0) f=-f;			// Negative Temp
		
		}
		else
		{
			// Der Widerstandswert soll gelesen werden
			d = DapiReadWord(handle, 0x4000 + ch*8 + 6);				// Widerstandswert lesen
	
			f = ((float) (d&0x7fff)) / 100;								// Faktor 100
			if(((d>>15)&1) != 0) f=-f;									// Negative
		}
	
		return f;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiCnt48ModeSet(long handle, long ch, long mode)
	{
		DapiWriteWord(handle, 0x5000 + ch*8 + 6, mode);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiCnt48ModeGet(long handle, long ch)
	{	
		return DapiReadWord(handle, 0x5000 + ch*8 + 6);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiCnt48CounterGet32(long handle, long ch)
	{
		return DapiReadLong(handle, 0x5000 + ch*8);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiPulseGenSet(long handle, long ch, long mode, long par0, long par1, long par2)
	{
	
		DapiWriteLong(handle, 0x5800 + ch*16 + 0 , mode);
		DapiWriteLong(handle, 0x5800 + ch*16 + 4 , par0);
		DapiWriteLong(handle, 0x5800 + ch*16 + 8 , par1);
		DapiWriteLong(handle, 0x5800 + ch*16 + 12, par2);
	
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiPWMOutSet(long handle, long ch, float data)
	{
		DapiWriteByte(handle, 0x0800 + ch  , (long) data);
	
	}
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiSpecialCommand(long handle, long cmd, long par1, long par2, long par3)
	{
		long ret=0;
		long cnt;
	
		switch((int) cmd)
		{
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_DA:
				switch((int) par1)
				{
					// ----------------------------------------
					case (int) DAPI_SPECIAL_DA_PAR_DA_LOAD_DEFAULT:
						DapiWriteByte(handle, 0x2000 + par2*8 + 7, 0x12);		// Auslieferungszustand laden
	
						cnt=100;
						do
						{
							--cnt;
						} while((DapiReadByte(handle, 0x2000 + par2*8 + 7) != 0xff) && (cnt!=0));
	
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_DA_PAR_DA_SAVE_EEPROM_CONFIG:
						DapiWriteByte(handle, 0x2000 + par2*8 + 7, 0x10);		// Ins EEPROM schreiben
	
						cnt=100;
						do
						{
							--cnt;
						} while((DapiReadByte(handle, 0x2000 + par2*8 + 7) != 0xff) && (cnt!=0));
	
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_DA_PAR_DA_LOAD_EEPROM_CONFIG:
						DapiWriteByte(handle, 0x2000 + par2*8 + 7, 0x11);		// Aus EEPROM laden
	
						cnt=100;
						do
						{
							--cnt;
						} while((DapiReadByte(handle, 0x2000 + par2*8 + 7) != 0xff) && (cnt!=0));
	
						break;
					// ----------------------------------------
				}
				break;
	
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_AD:
				switch((int) par1)
				{
					// ----------------------------------------
					case (int) DAPI_SPECIAL_AD_READ_MULTIPLE_AD:
						if(par2>64) return -1;
						if(par3>64) return -1;
						if(par2>par3) return -1;
						

						byte[] temp_ad_buffer = new byte[((int)par3-(int)par2 + 1)*4];
						ret=DapiReadMultipleBytes(handle, 0x1000 + par2*4, (par3-par2+1)*4, 1, temp_ad_buffer, temp_ad_buffer.length);
						System.arraycopy(temp_ad_buffer, 0, global_ad_buffer, ((int)par2*4), temp_ad_buffer.length);
						
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_AD_FIFO_DEACTIVATE:
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_enable_disable | (0<<8));
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_AD_FIFO_ACTIVATE:
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_enable_disable | (1<<8));
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_AD_FIFO_INIT:
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_init | (0<<8));
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_AD_FIFO_SET_INTERVAL_MS:
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_set_intervalL | ((par3&255)           <<8));
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_set_intervalH | ((par3>>8) & 255)     <<8);
						break;
					// ----------------------------------------
					case (int) DAPI_SPECIAL_AD_FIFO_SET_CHANNEL:
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_enable_chL | ((par3&255)          <<8));
						DapiWriteWord(handle, 0x1400 + par2*4, RO_FIFO_CMD_fifo_enable_chH | ((par3>>8) & 255)    <<8);
						break;
					// ----------------------------------------
				}
				break;

			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
	
	
						
						
			// ------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_SET_DIR_DX_8:
				DapiWriteLong(handle, 0x100 + par1/64, par2);
				break;
	
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_SET_DIR_DX_1:
				DapiWriteLong(handle, 0x120 + par1/8, par2);
				break;
	
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_GET_MODULE_CONFIG:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DO:
					ret = DapiReadByte(handle, 0xff00);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI:
					ret = DapiReadByte(handle, 0xff02);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI_FF:
					ret = DapiReadByte(handle, 0xff0c);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI_COUNTER:
					ret = DapiReadByte(handle, 0xff0e);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DX:
					ret = DapiReadByte(handle, 0xff04);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_AD:
					ret = DapiReadByte(handle, 0xff08);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DA:
					ret = DapiReadByte(handle, 0xff06);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_STEPPER:
					ret = DapiReadByte(handle, 0xff0a);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_TEMP:
					ret = DapiReadByte(handle, 0xff10);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_CNT48:
					ret = DapiReadByte(handle, 0xff12);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_PULSE_GEN:
					ret = DapiReadByte(handle, 0xff14);
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_PWM_OUT:
					ret = DapiReadByte(handle, 0xff16);
					break;
	
				}
				break;
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_GET_MODULE_VERSION:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_GET_MODULE_PAR_VERSION_0:
					ret = DapiReadByte(handle, 0xfff4) - '0';
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_PAR_VERSION_1:
					ret = DapiReadByte(handle, 0xfff5) - '0';
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_PAR_VERSION_2:
					ret = DapiReadByte(handle, 0xfff6) - '0';
					break;
	
				case (int) DAPI_SPECIAL_GET_MODULE_PAR_VERSION_3:
					ret = DapiReadByte(handle, 0xfff7) - '0';
					break;
				}
				break;
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_TIMEOUT:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_TIMEOUT_SET_VALUE_SEC:
					DapiWriteWord(handle, 0xfd02, par2*10+ (par3%10));
					break;
	
				case (int) DAPI_SPECIAL_TIMEOUT_ACTIVATE:
					DapiWriteByte(handle, 0xfd00,1);
					break;
	
				case (int) DAPI_SPECIAL_TIMEOUT_DEACTIVATE:
					DapiWriteByte(handle, 0xfd00,0);
					break;
	
				case (int) DAPI_SPECIAL_TIMEOUT_GET_STATUS:
					ret = DapiReadByte(handle, 0xfd01);
					break;
				}
				break;
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_DI:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_DI_FF_FILTER_VALUE_SET:
					DapiWriteByte(handle, 0xfd10, par2);
					break;
				case (int) DAPI_SPECIAL_DI_FF_FILTER_VALUE_GET:
					ret = DapiReadByte(handle, 0xfd10);
					break;
				}
				break;
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_WATCHDOG:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_MSEC:
					DapiWriteLong(handle, 0xe004, par2);
					break;
	
				case (int) DAPI_SPECIAL_WATCHDOG_GET_TIMEOUT_MSEC:
					ret = DapiReadLong(handle, 0xe004);
					break;
	
				case (int) DAPI_SPECIAL_WATCHDOG_GET_STATUS:
					ret=DapiReadByte(handle, 0xe000);
					break;
	
				case (int) DAPI_SPECIAL_WATCHDOG_GET_WD_COUNTER_MSEC:
					ret = DapiReadLong(handle, 0xe008);
					break;
	
				case (int) DAPI_SPECIAL_WATCHDOG_GET_TIMEOUT_RELAIS_COUNTER_MSEC:
					ret = DapiReadLong(handle, 0xe00c);
					break;
	
				case (int) DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_REL1_COUNTER_MSEC:
					DapiWriteLong(handle, 0xe008, par2);
					break;
	
				case (int) DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_REL2_COUNTER_MSEC:
					DapiWriteLong(handle, 0xe00c, par2);
					break;
	
				
				}
				break;
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_COUNTER:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_COUNTER_LATCH_ALL:
					DapiWriteByte(handle, 0xfe12, 0x19);					// Latch all Counter
					break;
				case (int) DAPI_SPECIAL_COUNTER_LATCH_ALL_WITH_RESET:
					DapiWriteByte(handle, 0xfe12, 0x1a);					// Latch all Counter WITH RESET
					break;
				}
				break;
	
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_CNT48:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_CNT48_RESET_SINGLE:
					DapiWriteByte(handle, 0x5000 + par2*8, 0x00);			// Reset Counter Nr. "par2"
					break;
				case (int) DAPI_SPECIAL_CNT48_RESET_GROUP8:
					DapiWriteByte(handle, 0x5003 + (par2/8)*0x40, 0x00);	// Reset Counter Group Nr. "par2"
					break;
				case (int) DAPI_SPECIAL_CNT48_LATCH_GROUP8:
					DapiWriteByte(handle, 0x5002 + (par2/8)*0x40, 0x00);	// Latch Counter Group Nr. "par2"
					break;
				case (int) DAPI_SPECIAL_CNT48_DI_GET1:
					ret = DapiReadByte(handle, 0x5007 + (par2*8)) & 1;			// Bit 0 Latch Counter Group Nr. "par2"
	
					break;
				}
				break;
				
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			// -----------------------------------------------------------------------------------------
			case (int) DAPI_SPECIAL_CMD_SOFTWARE_FIFO:
				switch((int) par1)
				{
				case (int) DAPI_SPECIAL_SOFTWARE_FIFO_ACTIVATE:
					DapiWriteByte(handle, 0xfe30, 0x01);					// activate software fifo
					break;
				case (int) DAPI_SPECIAL_SOFTWARE_FIFO_DEACTIVATE:
					DapiWriteByte(handle, 0xfe30, 0x00);					// deactivate software fifo
					break;	
				case (int) DAPI_SPECIAL_SOFTWARE_FIFO_GET_STATUS:
					ret = DapiReadByte(handle, 0xfe30) & 1;					// read software-fifo status (activated / deactivated)
					break;
				}
				break;			
		}
		
		return ret;
	}
	
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiStepperCommand(long handle, long motor, long cmd, long par1, long par2, long par3, long par4)
	{
		long ret;
		long adr_base = (0x3000 | (motor*0x40));
		ret=0;
		byte[] buff = new byte[20];
		int pos;
	
		pos = 0;
	
		
		buff[pos++] = (byte) ((par3)     & 0xff);
		buff[pos++] = (byte) ((par3>>8)  & 0xff);
		buff[pos++] = (byte) ((par3>>16) & 0xff);
		buff[pos++] = (byte) ((par3>>24) & 0xff);
	
		buff[pos++] = (byte) ((par2)     & 0xff);
		buff[pos++] = (byte) ((par2>>8)  & 0xff);
		buff[pos++] = (byte) ((par2>>16) & 0xff);
		buff[pos++] = (byte) ((par2>>24) & 0xff);
	
		buff[pos++] = (byte) ((par1)     & 0xff);
		buff[pos++] = (byte) ((par1>>8)  & 0xff);
		buff[pos++] = (byte) ((par1>>16) & 0xff);
		buff[pos++] = (byte) ((par1>>24) & 0xff);
	
		buff[pos++] = (byte) ((cmd)     & 0xff);
		buff[pos++] = (byte) ((cmd>>8)  & 0xff);
		buff[pos++] = (byte) ((cmd>>16) & 0xff);
		buff[pos++] = (byte) ((cmd>>24) & 0xff);
		
		
		DapiWriteMultipleBytes(handle, adr_base + 0x10 , pos, 1, buff , pos);
	
	
		if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_0_BYTES)
		{
			ret=0;
		}
		else if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_1_BYTES)
		{
			ret=DapiReadByte(handle, (adr_base | 0x20));
		}
		else if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_2_BYTES)
		{
			ret=DapiReadWord(handle, (adr_base | 0x20));
		}
		else if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_4_BYTES)
		{
			ret=DapiReadLong(handle, (adr_base | 0x20));
		}
		
		
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------

	public long DapiStepperCommandEx(long handle, long motor, long cmd, long par1, long par2, long par3, long par4, long par5, long par6, long par7)
	{
		long ret;
		long adr_base = 0x3000 | motor*0x40;
		ret=0;
	
	
	
		byte[] buff = new byte[40];
		int pos;
	
		pos = 0;
	
		buff[pos++] = (byte) ((par7)     & 0xff);
		buff[pos++] = (byte) ((par7>>8)  & 0xff);
		buff[pos++] = (byte) ((par7>>16) & 0xff);
		buff[pos++] = (byte) ((par7>>24) & 0xff);
	
		buff[pos++] = (byte) ((par6)     & 0xff);
		buff[pos++] = (byte) ((par6>>8)  & 0xff);
		buff[pos++] = (byte) ((par6>>16) & 0xff);
		buff[pos++] = (byte) ((par6>>24) & 0xff);
	
		buff[pos++] = (byte) ((par5)     & 0xff);
		buff[pos++] = (byte) ((par5>>8)  & 0xff);
		buff[pos++] = (byte) ((par5>>16) & 0xff);
		buff[pos++] = (byte) ((par5>>24) & 0xff);
	
		buff[pos++] = (byte) ((par4)     & 0xff);
		buff[pos++] = (byte) ((par4>>8)  & 0xff);
		buff[pos++] = (byte) ((par4>>16) & 0xff);
		buff[pos++] = (byte) ((par4>>24) & 0xff);
	
		buff[pos++] = (byte) ((par3)     & 0xff);
		buff[pos++] = (byte) ((par3>>8)  & 0xff);
		buff[pos++] = (byte) ((par3>>16) & 0xff);
		buff[pos++] = (byte) ((par3>>24) & 0xff);
	
		buff[pos++] = (byte) ((par2)     & 0xff);
		buff[pos++] = (byte) ((par2>>8)  & 0xff);
		buff[pos++] = (byte) ((par2>>16) & 0xff);
		buff[pos++] = (byte) ((par2>>24) & 0xff);
	
		buff[pos++] = (byte) ((par1)     & 0xff);
		buff[pos++] = (byte) ((par1>>8)  & 0xff);
		buff[pos++] = (byte) ((par1>>16) & 0xff);
		buff[pos++] = (byte) ((par1>>24) & 0xff);
	
		buff[pos++] = (byte) ((cmd)     & 0xff);
		buff[pos++] = (byte) ((cmd>>8)  & 0xff);
		buff[pos++] = (byte) ((cmd>>16) & 0xff);
		buff[pos++] = (byte) ((cmd>>24) & 0xff);
	
		DapiWriteMultipleBytes(handle, adr_base + 0x00 , pos, 1, buff , pos);
	
		if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_0_BYTES)
		{
			ret=0;
		}
		else if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_1_BYTES)
		{
			ret=DapiReadByte(handle, (adr_base | 0x20));
		}
		else if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_2_BYTES)
		{
			ret=DapiReadWord(handle, (adr_base | 0x20));
		}
		else if ((cmd & 0xc0000000) == DAPI_STEPPER_RETURN_4_BYTES)
		{
			ret=DapiReadLong(handle, (adr_base | 0x20));
		}
		
	
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiStepperGetStatus(long handle, long motor, long cmd)
	{
		long ret;
		long adr_base = 0x3000 | motor*0x40;
		ret=0;

		if (cmd == DAPI_STEPPER_STATUS_GET_POSITION)
		{
			ret = DapiReadLong(handle, (adr_base | 0x28));
		}
		else if (cmd == DAPI_STEPPER_STATUS_GET_SWITCH)
		{
			ret = DapiReadByte(handle, (adr_base | 0x2f));
		}
		else if (cmd == DAPI_STEPPER_STATUS_GET_ACTIVITY)
		{
			ret = DapiReadByte(handle, (adr_base | 0x2e));
		}
		
		return ret;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiWatchdogEnable(long handle)
	{
		DapiWriteByte(handle, 0xe000, 0x23);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiWatchdogDisable(long handle)
	{
		DapiWriteByte(handle, 0xe000, 0x12);
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiWatchdogRetrigger(long handle)
	{
		DapiWriteByte(handle, 0xe001, 0x34);
	}
	
	
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiWriteByte(long handle, long address, long value) 
	{
		int tx_pos = 0;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// socket
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++] = (byte) 'W';							// COMMAND
		tx_buffer[tx_pos++] = (byte) 'B';							// WIDTH
		
		tx_buffer[tx_pos++] = (byte) ((address >> 8) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((address >> 0) & 0xff);		// ADDRESS_BIT_0_7
		
		tx_buffer[tx_pos++] = (byte) (value & 0xff);				// DATA_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				//LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				//LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}	
		
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiWriteWord(long handle, long address, long value) {
		
		int tx_pos = 0;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++] = (byte) 'W';							// COMMAND
		tx_buffer[tx_pos++] = (byte) 'W';							// WIDTH
		
		tx_buffer[tx_pos++] = (byte) ((address >> 8) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((address >> 0) & 0xff);		// ADDRESS_BIT_0_7
		
		tx_buffer[tx_pos++] = (byte) ((value >> 8) & 0xff);			// DATA_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((value >> 0) & 0xff);			// DATA_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				//LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				//LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}	
		
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void DapiWriteLong(long handle, long address, long value) 
	{		
		int tx_pos = 0;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++] = (byte) 'W';							// COMMAND
		tx_buffer[tx_pos++] = (byte) 'L';							// WIDTH
		
		tx_buffer[tx_pos++] = (byte) ((address >> 8) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((address >> 0) & 0xff);		// ADDRESS_BIT_0_7
		
		tx_buffer[tx_pos++] = (byte) ((value >> 24) & 0xff);		// DATA_BIT_24_31
		tx_buffer[tx_pos++] = (byte) ((value >> 16) & 0xff);		// DATA_BIT_16_23
		tx_buffer[tx_pos++] = (byte) ((value >> 8 ) & 0xff);		// DATA_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((value >> 0 ) & 0xff);		// DATA_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				//LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				//LENGTH_BIT_0_7
		
		if (job_id > 255) {
			job_id = 0;
		}	
		
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
		}
		
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiReadByte(long handle, long address) 
	{
		int tx_pos = 0;
		int rx_pos = 7;
		long data;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++] = (byte) 'R';							// COMMAND
		tx_buffer[tx_pos++] = (byte) 'B';							// WIDTH
		
		tx_buffer[tx_pos++] = (byte) ((address >> 8) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((address >> 0) & 0xff);		// ADDRESS_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				//LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				//LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}	
	
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
			
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
			return 0;
		}
		
		// calculate data
		data = ((rx_buffer[rx_pos++] << 0 ) & 0xffL);
		
		return data;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiReadWord(long handle, long address) 
	{
		int tx_pos = 0;
		int rx_pos = 7;
		long data;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++] = (byte) 'R';							// COMMAND
		tx_buffer[tx_pos++] = (byte) 'W';							// WIDTH
		
		tx_buffer[tx_pos++] = (byte) ((address >> 8) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((address >> 0) & 0xff);		// ADDRESS_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				//LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				//LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}	
		
		
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
			return 0;
		}
		
		// calculate data
		data = 	((rx_buffer[rx_pos++] << 0 ) & 0xffL);
		data += ((rx_buffer[rx_pos++] << 8 ) & 0xff00L);
		
		return data;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiReadLong(long handle, long address) 
	{
		int tx_pos = 0;
		int rx_pos = 7;
		long data;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++] = (byte) 'R';							// COMMAND
		tx_buffer[tx_pos++] = (byte) 'L';							// WIDTH
		
		tx_buffer[tx_pos++] = (byte) ((address >> 8) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++] = (byte) ((address >> 0) & 0xff);		// ADDRESS_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				// LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				// LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}
		
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
			return 0;
		}
		
		// calculate data
		data = 	((rx_buffer[rx_pos++] << 0 ) & 0xffL);
		data += ((rx_buffer[rx_pos++] << 8 ) & 0xff00L);
		data += ((rx_buffer[rx_pos++] << 16) & 0xff0000L);
		data += ((rx_buffer[rx_pos++] << 24) & 0xff000000L);

		return data;
	}
	
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiReadMultipleBytes(long handle, long address, long address_depth, long increment, byte[] buff, long buff_len)
	{
		long i;
		long j;
		
		int pos = 0;
		int tx_pos = 0;
		int tx_cnt;
		
		long m_start_id;
		long m_data_id;
		long m_data_pos;
		
		// tx_buffer zusammenbauen
		tx_cnt=0;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1
		
		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID
		
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
	
		tx_buffer[tx_pos++]='R';									// COMMAND
		tx_buffer[tx_pos++]='M';									// WIDTH
	
		// address (2byte)
		tx_buffer[tx_pos++]= (byte) ((address >> 8 ) & 0xff);		// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++]= (byte) ((address >> 0 ) & 0xff);		// ADDRESS_BIT_0_7
		
		// address_depth (2byte)
		tx_buffer[tx_pos++]= (byte) ((address_depth >> 8 ) & 0xff);	// ADDRESS_DEPTH_BIT_8_15
		tx_buffer[tx_pos++]= (byte) ((address_depth >> 0 ) & 0xff);	// ADDRESS_DEPTH_BIT_0_7
		
		// increment (2byte)
		tx_buffer[tx_pos++]= (byte) ((increment >> 8 ) & 0xff);		// INCREMENT_BIT_8_15
		tx_buffer[tx_pos++]= (byte) ((increment >> 0 ) & 0xff);		// INCREMENT_BIT_0_7
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				// LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				// LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}
		
		try 
		{
			output_stream.write(tx_buffer, 0, tx_pos);
			output_stream.flush();
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
			return 1;
		}
	
		// data_temp �berpr�fen
		// datenblock = start_id_multiple_byte(1) +  data_id(2) + addr_depth	
	
		for (i=0; i<increment; i++)
		{
			pos = 0;
			m_data_pos = i*(address_depth+3);
			
			// start_id + data_id aus dem buffer filtern
			m_start_id = rx_buffer[(int) (7 + (m_data_pos + (pos++)))];
			m_data_id = rx_buffer[(int) (7 + (m_data_pos + (pos++)))];
			m_data_id = (m_data_id << 8) | rx_buffer[(int) (7 + (m_data_pos + (pos++)))];
			
			// start_id checken
			if (m_start_id != DEDITEC_TCP_START_ID_FOR_MULTIPLE_BYTE_DATA)
			{
				return 1;			// Error
			}
			
			// data_id checken
			if (m_data_id != i)
			{
				return 1;			// Error
			}
			
		}
		
		// daten sind ok - daten in "richtigen buffer" schreiben
		tx_cnt = 0;
		
		for (i=0; i< increment; i++)
		{
			for (j=0; j<address_depth; j++)
			{
				buff[tx_cnt++] = rx_buffer[(int) (7 + 3 + j + i*(address_depth+3))];
			}
		}
	
		return 0;
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public long DapiWriteMultipleBytes(long handle, long address, long address_depth, long increment, byte[] buff, long buff_len)
	{
		long i;
		long j;
		
		int pos = 0;
		int tx_pos = 0;
		
		tx_buffer[tx_pos++] = (byte) 0x63;							// Packet_ID_o
		tx_buffer[tx_pos++] = (byte) 0x9a;							// Packet_ID_1

		tx_buffer[tx_pos++] = (byte) 0x01;							// TCP_CMD_RO_1
		tx_buffer[tx_pos++] = (byte) job_id++;						// JOB_ID

		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit8-15
		tx_buffer[tx_pos++] = (byte) 0;								// Placeholder for LENGTH Bit0-7
		
		tx_buffer[tx_pos++]='W';									// COMMAND //Write
		tx_buffer[tx_pos++]='M';									// WIDTH //Multiple

		// address (2byte)
		tx_buffer[tx_pos++]= (byte) ((address >> 8 ) & 0xff);	// ADDRESS_BIT_8_15
		tx_buffer[tx_pos++]= (byte) ((address >> 0 ) & 0xff);	// ADDRESS_BIT_0_7
		
		// address_depth (2byte)
		tx_buffer[tx_pos++]= (byte) ((address_depth >> 8 ) & 0xff);	// ADDRESS_DEPTH_BIT_8_15
		tx_buffer[tx_pos++]= (byte) ((address_depth >> 0 ) & 0xff);	// ADDRESS_DEPTH_BIT_0_7
		
		// increment (2byte)
		tx_buffer[tx_pos++]= (byte) ((increment >> 8 ) & 0xff);	// INCREMENT_BIT_8_15
		tx_buffer[tx_pos++]= (byte) ((increment >> 0 ) & 0xff);	// INCREMENT_BIT_0_7
		
		// data start
		for (i=0; i<increment; i++)
		{
			// START_ID_MULTIPLE_BYTE (1byte)
			tx_buffer[tx_pos++]= (byte) DEDITEC_TCP_START_ID_FOR_MULTIPLE_BYTE_DATA;		// START_ID_MULTIPLE_BYTE
			
			// DATA_ID (2byte)
			tx_buffer[tx_pos++]= (byte) ((i >> 8 ) & 0xff);			// DATA_ID_BIT_8_15
			tx_buffer[tx_pos++]= (byte) ((i >> 0 ) & 0xff);			// DATA_ID_BIT_0_7
			
			// data	
			for (j=0; j<address_depth; j++)
			{
				tx_buffer[tx_pos++] = buff[pos++];
			}
		}
		// data end
		
		
		tx_buffer[4] = (byte) ((tx_pos >> 8) & 0xff);				//LENGTH_BIT_8_15
		tx_buffer[5] = (byte) ((tx_pos >> 0) & 0xff);				//LENGTH_BIT_0_7
		
		if (job_id > 255) 
		{
			job_id = 0;
		}
		
		try 
		{
			output_stream.write(tx_buffer);
			output_stream.flush();			
			
			amount_bytes_received = input_stream.read(rx_buffer);
			CheckIsValidDeditecAnswer();
		}
		catch (IOException ioe)
		{
			SetError(DAPI_ERR_DEVICE_NOT_FOUND);
			return 1;
		}
		
		return 0;
	
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void CheckIsValidDeditecAnswer()
	{
		int recv_pos = 0;
		
		// PACKET_ID_0 checken
		if((rx_buffer[recv_pos++] & 0xFFL) != DEDITEC_PACKET_ID_0)
		{
			SetError(DAPI_ERR_COMMUNICATION_ERROR);
			return;
		}
		
		// PACKET_ID_1 checken
		if((rx_buffer[recv_pos++] & 0xFFL) != DEDITEC_PACKET_ID_1)
		{
			SetError(DAPI_ERR_COMMUNICATION_ERROR);
			return;
		}
		
		// TCP_ANSWER_RO_1 checken
		if((rx_buffer[recv_pos++] & 0xFFL) != DEDITEC_TCP_ANSWER_RO_1)
		{
			SetError(DAPI_ERR_COMMUNICATION_ERROR);
			return;
		}
		
		// TCP_ANSWER_OK checken
		if((rx_buffer[recv_pos++] & 0xFFL) != DEDITEC_TCP_ANSWER_OK)
		{
			SetError(DAPI_ERR_COMMUNICATION_ERROR);
			return;
		}
	}
	
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public void SetError(long errorCode)
	{
		dapi_last_error = errorCode;
		
		switch ((int)errorCode)
		{
			case (int) DAPI_ERR_DEVICE_NOT_FOUND:
				dapi_last_error_text = "Device not found!";
				break;
				
			default:
				break;
		}
	}

	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	
	public boolean IsError()
	{
		if (dapi_last_error != DAPI_ERR_NONE)
		{
			System.out.printf("Error - Error Code = 0x%x\nMessage = %s\n", dapi_last_error,dapi_last_error_text);
		
			// clear last error
			dapi_last_error = DAPI_ERR_NONE;
			
			return true;
		}
		
		return false;
	}

	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
	// ----------------------------------------------------------------------------
}
	