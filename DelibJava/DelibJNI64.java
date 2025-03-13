package DelibJava;

//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//***********************************************************************************
//
//
//
//  DelibJNI64.java
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

public class DelibJNI64
{
    static 
    {
        System.loadLibrary("DelibJNI64");
    }
    
    // all Module-ID's
    public static final int USB_Interface8                  = 1;            // USB-Controller8/USB-TTL-IN8-OUT8
    public static final int USB_CAN_STICK                   = 2;            // USB-CAN-Stick
    public static final int USB_LOGI_500                    = 3;            // USB-LOGI-500/USB-LOGI-250
    public static final int USB_SER_DEBUG                   = 4;            // USB-SER-DEBUG
    public static final int RO_SER                          = 5;            // RO-SER-Serie
    public static final int USB_BITP_200                    = 6;            // USB-BITP-200
    public static final int RO_USB1                         = 7;            // RO-USB-Serie
    public static final int RO_USB                          = 7;            // RO-USB-Serie
    public static final int RO_ETH                          = 8;            // RO-ETH-Serie
    public static final int USB_MINI_STICK                  = 9;            // USB-MINI-Stick-Serie
    public static final int USB_LOGI_18                     = 10;           // USB-LOGI-100
    public static final int RO_CAN                          = 11;           // RO-CAN-Serie
    public static final int USB_SPI_MON                     = 12;           // USB_SPI_MON
    public static final int USB_WATCHDOG                    = 13;           // USB_Watchdog
    public static final int USB_OPTOIN_8                    = 14;           // USB-OPTOIN8 / USB-RELAIS-8
    public static final int USB_RELAIS_8                    = 14;           // USB-OPTOIN8 / USB-RELAIS-8
    public static final int USB_OPTOIN_8_RELAIS_8           = 15;           // USB-OPTOIN-8-RELAIS-8
    public static final int USB_OPTOIN_16_RELAIS_16         = 16;           // USB-OPTOIN-16-RELAIS-16
    public static final int USB_OPTOIN_32                   = 16;           // USB-OPTOIN-16-RELAIS-16
    public static final int USB_RELAIS_32                   = 16;           // USB-OPTOIN-16-RELAIS-16
    public static final int USB_OPTOIN_32_RELAIS_32         = 17;           // USB-OPTOIN-32-RELAIS-32
    public static final int USB_OPTOIN_64                   = 17;           // USB-OPTOIN-32-RELAIS-32
    public static final int USB_RELAIS_64                   = 17;           // USB-OPTOIN-32-RELAIS-32

    public static final int BS_USB_8                        = 15;
    public static final int BS_USB_16                       = 16;
    public static final int BS_USB_32                       = 17;

    public static final int USB_TTL_32                      = 18;           // USB-TTL-32
    public static final int USB_TTL_64                      = 18;           // USB-TTL-64
    public static final int RO_ETH_INTERN                   = 19;

    public static final int BS_SER                          = 20;
    public static final int BS_CAN                          = 21;
    public static final int BS_ETH                          = 22;

    public static final int NET_ETH                         = 23;

    public static final int RO_CAN2                         = 24;           // RO-CPU2 / 480 MBit/sec - CAN VERSION
    public static final int RO_USB2                         = 25;           // RO-CPU2 / 480 MBit/sec - USB/SER Version
    public static final int RO_ETH_LC                       = 26;           // RO-ETH-LC

    public static final int ETH_RELAIS_8                    = 27;
    public static final int ETH_OPTOIN_8                    = 27;
    public static final int ETH_O4_R4_ADDA                  = 28;

    public static final int ETHERNET_MODULE                 = 29;

    public static final int ETH_TTL_64                      = 30;

    public static final int NET_USB2                        = 31;
    public static final int NET_ETH_LC                      = 32;
    public static final int NET_USB1                        = 33;
    public static final int NET_SER                         = 34;
    
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // Declarations
    
    // ----------------------------------------------------------------------------
    // Special Function-Codes
    public static final int DAPI_SPECIAL_CMD_GET_MODULE_CONFIG                          = 1;
    public static final int DAPI_SPECIAL_CMD_TIMEOUT                                    = 2;
    public static final int DAPI_SPECIAL_CMD_DI                                         = 10;
    public static final int DAPI_SPECIAL_CMD_SET_DIR_DX_1                               = 3;
    public static final int DAPI_SPECIAL_CMD_SET_DIR_DX_8                               = 4;
    public static final int DAPI_SPECIAL_CMD_GET_MODULE_VERSION                         = 5;
    public static final int DAPI_SPECIAL_CMD_DA                                         = 6;
    public static final int DAPI_SPECIAL_CMD_WATCHDOG                                   = 7;
    public static final int DAPI_SPECIAL_CMD_COUNTER                                    = 8;
    public static final int DAPI_SPECIAL_CMD_AD                                         = 9;
    public static final int DAPI_SPECIAL_CMD_CNT48                                      = 11;
    public static final int DAPI_SPECIAL_CMD_SOFTWARE_FIFO                              = 12;
    public static final int DAPI_SPECIAL_CMD_MODULE_REBOOT                              = 13;
    public static final int DAPI_SPECIAL_CMD_MODULE_RESCAN                              = 14;
    public static final int DAPI_SPECIAL_CMD_RESTART_CHECK_MODULE_CONFIG                = 15;
    public static final int DAPI_SPECIAL_CMD_PWM                                        = 19;

    // values for PAR1
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI                       = 1;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI_FF                    = 7;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DI_COUNTER               = 8;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DO                       = 2;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DX                       = 3;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_AD                       = 4;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_DA                       = 5;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_TEMP                     = 9;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_STEPPER                  = 6;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_CNT48                    = 10;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_PULSE_GEN                = 11;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_PWM_OUT                  = 12;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_HW_INTERFACE1            = 13;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_SW_FEATURE1              = 14;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_HW_GROUP                 = 15;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_SW_CLASS                 = 16;
    public static final int DAPI_SPECIAL_GET_MODULE_CONFIG_PAR_MODULE_ID                = 17;
    //
    public static final int DAPI_SPECIAL_GET_MODULE_PAR_VERSION_0                       = 0;
    public static final int DAPI_SPECIAL_GET_MODULE_PAR_VERSION_1                       = 1;
    public static final int DAPI_SPECIAL_GET_MODULE_PAR_VERSION_2                       = 2;
    public static final int DAPI_SPECIAL_GET_MODULE_PAR_VERSION_3                       = 3;
    //
    public static final int DAPI_SPECIAL_TIMEOUT_SET_VALUE_SEC                          = 1;
    public static final int DAPI_SPECIAL_TIMEOUT_ACTIVATE                               = 2;
    public static final int DAPI_SPECIAL_TIMEOUT_DEACTIVATE                             = 3;
    public static final int DAPI_SPECIAL_TIMEOUT_GET_STATUS                             = 4;
    public static final int DAPI_SPECIAL_TIMEOUT_DO_VALUE_LOAD_DEFAULT					= 5;
    public static final int DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_WR_CLR32					= 6;
    public static final int DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_RD_CLR32					= 7;
    public static final int DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_WR_SET32					= 8;
    public static final int DAPI_SPECIAL_TIMEOUT_DO_VALUE_MASK_RD_SET32					= 9;
    //
    public static final int DAPI_SPECIAL_DI_FF_FILTER_VALUE_SET                         = 1;
    public static final int DAPI_SPECIAL_DI_FF_FILTER_VALUE_GET                         = 2;
    //
    public static final int DAPI_SPECIAL_AD_READ_MULTIPLE_AD                            = 1;
    public static final int DAPI_SPECIAL_AD_FIFO_ACTIVATE                               = 2;
    public static final int DAPI_SPECIAL_AD_FIFO_DEACTIVATE                             = 3;
    public static final int DAPI_SPECIAL_AD_FIFO_GET_STATUS                             = 4;
    public static final int DAPI_SPECIAL_AD_FIFO_SET_INTERVAL_MS                        = 5;
    public static final int DAPI_SPECIAL_AD_FIFO_SET_CHANNEL                            = 6;
    public static final int DAPI_SPECIAL_AD_FIFO_INIT                                   = 7;
    public static final int DAPI_SPECIAL_AD_FILTER_SET                                  = 8;
    //
    public static final int DAPI_SPECIAL_DA_PAR_DA_LOAD_DEFAULT                         = 1;
    public static final int DAPI_SPECIAL_DA_PAR_DA_SAVE_EEPROM_CONFIG                   = 2;
    public static final int DAPI_SPECIAL_DA_PAR_DA_LOAD_EEPROM_CONFIG                   = 3;
    //
    public static final int DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_MSEC                      = 1;
    public static final int DAPI_SPECIAL_WATCHDOG_GET_TIMEOUT_MSEC                      = 2;
    public static final int DAPI_SPECIAL_WATCHDOG_GET_STATUS                            = 3;
    public static final int DAPI_SPECIAL_WATCHDOG_GET_WD_COUNTER_MSEC                   = 4;
    public static final int DAPI_SPECIAL_WATCHDOG_GET_TIMEOUT_RELAIS_COUNTER_MSEC       = 5;
    public static final int DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_REL1_COUNTER_MSEC         = 6;
    public static final int DAPI_SPECIAL_WATCHDOG_SET_TIMEOUT_REL2_COUNTER_MSEC         = 7;
    //
    public static final int DAPI_SPECIAL_COUNTER_LATCH_ALL                              = 1;
    public static final int DAPI_SPECIAL_COUNTER_LATCH_ALL_WITH_RESET                   = 2;
    //
    public static final int DAPI_SPECIAL_CNT48_RESET_SINGLE                             = 1;
    public static final int DAPI_SPECIAL_CNT48_RESET_GROUP8                             = 2;
    public static final int DAPI_SPECIAL_CNT48_LATCH_GROUP8                             = 3;
    public static final int DAPI_SPECIAL_CNT48_DI_GET1                                  = 4;
    //
    public static final int DAPI_SPECIAL_SOFTWARE_FIFO_ACTIVATE                         = 1;
    public static final int DAPI_SPECIAL_SOFTWARE_FIFO_DEACTIVATE                       = 2;
    public static final int DAPI_SPECIAL_SOFTWARE_FIFO_GET_STATUS                       = 3;
    //
    public static final int DAPI_SPECIAL_PWM_FREQ_LOAD_DFAULT                           = 1;
    public static final int DAPI_SPECIAL_PWM_FREQ_SAVE_EEPROM                           = 2;
    public static final int DAPI_SPECIAL_PWM_FREQ_LOAD_EEPROM                           = 3;
    public static final int DAPI_SPECIAL_PWM_FREQ_SET                                   = 4;

    // values for PAR2
    public static final int DAPI_SPECIAL_AD_CH0_CH15                                    = 0;
    public static final int DAPI_SPECIAL_AD_CH16_CH31                                   = 1;
    public static final int DAPI_SPECIAL_AD_CH32_CH47                                   = 2;
    public static final int DAPI_SPECIAL_AD_CH48_CH63                                   = 3;

    // ----------------------------------------------------------------------------
    // DapiScanModules-Codes
    public static final int DAPI_SCANMODULE_GET_MODULES_AVAILABLE                       = 1;

    // ----------------------------------------------------------------------------
    // SOFTWARE Features Class
    public static final int DAPI_SW_CLASS_TYPE_OF_MODULE_IDENTIFICATION                 = (1<<0);           // Bit 0
    public static final int DAPI_SW_CLASS_DI_DO_DX_CHANNELS                             = (1<<1);           // Bit 1
    public static final int DAPI_SW_CLASS_AD_DA_CHANNELS                                = (1<<2);           // Bit 2

    // ----------------------------------------------------------------------------
    // Hardware Group
    public static final int DAPI_HW_GROUP_RO                                            = (1<<0);           // Bit 0
    public static final int DAPI_HW_GROUP_LOGICANALYZER                                 = (1<<1);           // Bit 1
    public static final int DAPI_HW_GROUP_DI_DO_AD_DA                                   = (1<<2);           // Bit 2
    public static final int DAPI_HW_GROUP_USB                                           = (1<<3);           // Bit 3
    public static final int DAPI_HW_GROUP_BS                                            = (1<<4);           // Bit 4
    public static final int DAPI_HW_GROUP_NET                                           = (1<<5);           // Bit 5
    public static final int DAPI_HW_GROUP_ETH                                           = (1<<6);           // Bit 6

    // --------------------------------------------------------
    // Software Feature Bits
    public static final int DAPI_SW_FEATURE_BIT_SUPPORTED_BY_FIRMWARE                   = 0x00000001;
    public static final int DAPI_SW_FEATURE_BIT_SOFTWARE_FIFO                           = 0x00000002;
    public static final int DAPI_SW_FEATURE_BIT_CFG_DO_CMD_SET_CLR_BIT_32               = 0x00000004;
    public static final int DAPI_SW_FEATURE_BIT_EEPROM_RN23                             = 0x00000008;
    public static final int DAPI_SW_FEATURE_BIT_EEPROM_E2_2K                            = 0x00000020;
    public static final int DAPI_SW_FEATURE_BIT_EEPROM_E2_32K                           = 0x00000040;
    public static final int DAPI_SW_FEATURE_BIT_EEPROM_FS_SUPPORT                       = 0x00000080;
    public static final int DAPI_SW_FEATURE_BIT_DX_1_MODE                               = 0x00000010;
    public static final int DAPI_SW_FEATURE_BIT_AUTO_OUTPUTS_OFF_TIMEOUT                = 0x02000000;
    public static final int DAPI_SW_FEATURE_BIT_AUTO_OUTPUTS_OFF_TIMEOUT_WITH_MASK		= 0x00008000;
    public static final int DAPI_SW_FEATURE_BIT_SUPP_INDIVIDUAL_CH_NAMES                = 0x00001000;
    public static final int DAPI_SW_FEATURE_BIT_DEV_IO_REG_ERR_SUPP                     = 0x00002000;
    public static final int DAPI_SW_FEATURE_BIT_SUPP_SYSTEM_INFO                        = 0x00004000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_WATCHDOG                            = 0x10000000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_DI                                  = 0x20000000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_DI_CNT                              = 0x40000000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_DI_CNT_LATCH                        = 0x00080000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_DI_FF                               = 0x80000000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_DO                                  = 0x01000000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_DO_WITH_TIMER                       = 0x00040000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_PWM_OUT                             = 0x04000000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_DX                                  = 0x08000000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_DA                                  = 0x00100000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_AD                                  = 0x00200000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_CNT_OUT32                           = 0x00400000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_CNT_IN48                            = 0x00800000;

    public static final int DAPI_SW_FEATURE_BIT_CFG_TEMP                                = 0x00010000;
    public static final int DAPI_SW_FEATURE_BIT_CFG_STEPPER                             = 0x00020000;

    // --------------------------------------------------------
    // Hardware Interface Bits
    public static final int DAPI_HW_INTERFACE_BIT_SUPPORTED_BY_FIRMWARE                 = 0x00000001;
    public static final int DAPI_HW_INTERFACE_BIT_CFG_ETH                               = 0x00000002;
    public static final int DAPI_HW_INTERFACE_BIT_CFG_CAN                               = 0x00000004;
    public static final int DAPI_HW_INTERFACE_BIT_CFG_RS232                             = 0x00000008;
    public static final int DAPI_HW_INTERFACE_BIT_CFG_RS232_RS485                       = 0x00000010;
    public static final int DAPI_HW_INTERFACE_BIT_CFG_USB1                              = 0x00000020;
    public static final int DAPI_HW_INTERFACE_BIT_CFG_USB2                              = 0x00000040;

    public static final int DAPI_HW_INTERFACE_PRODUCT_ID_MASK                           = 0xFF000000;
    public static final int DAPI_HW_INTERFACE_PRODUCT_ID_RO                             = 0x01000000;
    public static final int DAPI_HW_INTERFACE_PRODUCT_ID_RO2                            = 0x02000000;
    public static final int DAPI_HW_INTERFACE_PRODUCT_ID_BS                             = 0x03000000;
    public static final int DAPI_HW_INTERFACE_PRODUCT_ID_NET                            = 0x04000000;
    public static final int DAPI_HW_INTERFACE_PRODUCT_ID_NET_IP                         = 0x05000000;

    // --------------------------------------------------------
    // TCP Feature Bits
    public static final int DAPI_TCP_FEATURE_BIT_SUPPORTED_BY_FIRMWARE                  = 0x00000001;
    public static final int DAPI_TCP_FEATURE_BIT_SUPP_RTC                               = 0x00000002;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // Prototypes for DELIB-Functions
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // 
    public static native int DapiOpenModule(int moduleID, int nr);
    // left out (not possible): DapiOpenModuleEx
    public static native int DapiCloseModule(int handle);

    public static native int DapiScanModule(int moduleID, int cmd);
    public static native int DapiScanAllModulesAvailable(int todo);
    public static native int DapiScanAllModulesAvailableListEcecute(int nr, int todo);

    public static native int DapiGetDELIBVersion(int mode, int par);

    public static native int DapiPing(int handle, int value);

    // ----------------------------------------------------------------------------
    // Register Access
    public static native void DapiWriteByte(int handle, int adress, int value);
    public static native void DapiWriteWord(int handle, int adress, int value);
    public static native void DapiWriteLong(int handle, int adress, int value);
    public static native void DapiWriteLongLong(int handle, int adress, long value);

    public static native int DapiReadByte(int handle, int adress);
    public static native int DapiReadWord(int handle, int adress);
    public static native int DapiReadLong(int handle, int adress);
    public static native long DapiReadLongLong(int handle, int adress);

    // ----------------------------------------------------------------------------
    // Error Handling
    public static native int DapiGetLastError();
    // left out (not possible): DapiGetLastErrorText
    public static native void DapiClearLastError();

    // ----------------------------------------------------------------------------
    // Digital Inputs
    public static native int DapiDIGet1(int handle, int ch);
    public static native int DapiDIGet8(int handle, int ch);
    public static native int DapiDIGet16(int handle, int ch);
    public static native int DapiDIGet32(int handle, int ch);
    public static native long DapiDIGet64(int handle, int ch);  

    public static native int DapiDIGetFF32(int handle, int ch);

    public static native int DapiDIGetCounter(int handle, int ch, int mode);

    // ----------------------------------------------------------------------------
    // Digital Outputs
    public static native void DapiDOSet1(int handle, int ch, int data);
    public static native void DapiDOSet8(int handle, int ch, int data);
    public static native void DapiDOSet16(int handle, int ch, int data);
    public static native void DapiDOSet32(int handle, int ch, int data);
    public static native void DapiDOSet64(int handle, int ch, long data);

    public static native int DapiDOReadback32(int handle, int ch);
    public static native long DapiDOReadback64(int handle, int ch);

    public static native void DapiDOSet1_WithTimer(int handle, int ch, int data, int time_ms);
    public static native void DapiDOSetBit32(int handle, int ch, int data);
    public static native void DapiDOClrBit32(int handle, int ch, int data);

    // ----------------------------------------------------------------------------
    // Analog Inputs
    public static native int DapiADGet(int handle, int ch);
    // left out (not possible): DapiADGetValueModeUnit
    public static native float DapiADGetVolt(int handle, int ch);
    public static native float DapiADGetmA(int handle, int ch);
    public static native void DapiADSetMode(int handle, int ch, int mode);
    public static native int DapiADGetMode(int handle, int ch);

    // ----------------------------------------------------------------------------
    // Analog Outputs
    public static native void DapiDASet(int handle, int ch, int data);
    public static native void DapiDASetVolt(int handle, int ch, float data);
    public static native void DapiDASetmA(int handle, int ch, float data);
    // left out (not possible): DapiDAGetValueModeUnit  
    public static native void DapiDASetMode(int handle, int ch, int mode);
    public static native int DapiDAGetMode(int handle, int ch);

    // ----------------------------------------------------------------------------
    // Temperature Inputs
    public static native float DapiTempGet(int handle, int ch);

    // ----------------------------------------------------------------------------
    // Counter48 Inputs
    public static native void DapiCnt48ModeSet(int handle, int ch, int mode);
    public static native int DapiCnt48ModeGet(int handle, int ch);
    public static native int DapiCnt48CounterGet32(int handle, int ch);
    public static native long DapiCnt48CounterGet48(int handle, int ch);

    // ----------------------------------------------------------------------------
    // Pulse-Generator Outputs
    public static native void DapiPulseGenSet(int handle, int ch, int mode, int par0, int par1, int par2);

    // ----------------------------------------------------------------------------
    // PWM Outputs
    public static native void DapiPWMOutSet(int handle, int ch, float data);
    
    // ----------------------------------------------------------------------------
    // Stepper
    public static native int DapiStepperCommand(int handle, int motor, int cmd, int par1, int par2, int par3, int par4);
    public static native int DapiStepperCommandEx(int handle, int motor, int cmd, int par1, int par2, int par3, int par4, int par5, int par6, int par7);
    public static native int DapiStepperGetStatus(int handle, int motor, int cmd);

    // ----------------------------------------------------------------------------
    // Watchdog
    public static native void DapiWatchdogEnable(int handle);
    public static native void DapiWatchdogDisable(int handle);
    public static native void DapiWatchdogRetrigger(int handle);

    // ----------------------------------------------------------------------------
    // Fifo
    // left out (not possible): DapiReadFifo

    // ----------------------------------------------------------------------------
    // CAN
    // left out (not possible): DAPI_CAN_MESSAGE_STRUCT
    // left out (not possible): DapiCANCommand
    // left out (not possible): DapiCANGetPacket
    // left out (not possible): DapiCANSendPacket

    // ----------------------------------------------------------------------------
    // Special
    public static native int DapiSpecialCommand(int handle, int cmd, int par1, int par2, int par3);
    // left out (not possible): DapiSpecialCommandExt

    // left out (not possible): DapiReadMultipleBytes
    // left out (not possible): DapiWriteMultipleBytes

    // ----------------------------------------------------------------------------
    // DI - Counter Mode
    public static final int DAPI_CNT_MODE_READ                              = 0x00;
    public static final int DAPI_CNT_MODE_READ_WITH_RESET                   = 0x01;
    public static final int DAPI_CNT_MODE_READ_LATCHED                      = 0x02;

    // ----------------------------------------------------------------------------
    // A/D and D/A Modes
    public static final int DAPI_ADDA_MODE_UNIPOL_10V                       = 0x00;
    public static final int DAPI_ADDA_MODE_UNIPOL_5V                        = 0x01;
    public static final int DAPI_ADDA_MODE_UNIPOL_2V5                       = 0x02;

    public static final int DAPI_ADDA_MODE_BIPOL_10V                        = 0x40;
    public static final int DAPI_ADDA_MODE_BIPOL_5V                         = 0x41;
    public static final int DAPI_ADDA_MODE_BIPOL_2V5                        = 0x42;

    public static final int DAPI_ADDA_MODE_0_20mA                           = 0x80;
    public static final int DAPI_ADDA_MODE_4_20mA                           = 0x81;
    public static final int DAPI_ADDA_MODE_0_24mA                           = 0x82;
    //public static final int DAPI_ADDA_MODE_0_25mA                         = 0x83;
    public static final int DAPI_ADDA_MODE_0_50mA                           = 0x84;

    public static final int DAPI_ADDA_MODE_BI_CAL_MODE                      = 0xfd;
    public static final int DAPI_ADDA_MODE_0_20mA_TESTMODE                  = 0xfe;
    public static final int DAPI_ADDA_MODE_BIPOL_10V_TESTMODE               = 0xff;

    public static final int DAPI_ADDA_MODE_DA_DISABLE                       = 0x100;
    public static final int DAPI_ADDA_MODE_DA_ENABLE                        = 0x200;

    public static final int DAPI_ADDA_MODE_PREVENT_DAPI_MODE_ERROR          = 0x8000;

    // --------------------------------------------------------
    // A/D and D/A units
    public static final int DAPI_ADDA_UNIT_ILLEGAL                          = 0x00;
    public static final int DAPI_ADDA_UNIT_VOLT                             = 0x01;
    public static final int DAPI_ADDA_UNIT_MA                               = 0x02;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // Stepper-Defines

    // ------------------------------------
    // ERROR Codes
    public static final int DAPI_STEPPER_ERR_NONE                           = 0;                // es liegt kein Fehler vor 
    public static final int DAPI_STEPPER_ERR_PARAMETER                      = 1;                // Parameter hat falschen Wertebereich 
    public static final int DAPI_STEPPER_ERR_MOTOR_MOVE                     = 2;                // Kommando abgelehnt, da sich der Motor dreht
    public static final int DAPI_STEPPER_ERR_DISABLE_MOVE                   = 3;                // Kommando abgehelnt, da Motorbewegung disabled ist
    public static final int DAPI_STEPPER_ERR_DEVICE_NOT_FOUND               = -1;       // es liegt kein Fehler vor 

    // ------------------------------------
    // Special Stepper Function-Codes
    public static final int DAPI_STEPPER_RETURN_0_BYTES                     = 0x00000000;       // Kommando schickt 0 Byte als Antwort
    public static final int DAPI_STEPPER_RETURN_1_BYTES                     = 0x40000000;       // Kommando schickt 1 Byte als Antwort
    public static final int DAPI_STEPPER_RETURN_2_BYTES                     = 0x80000000;       // Kommando schickt 2 Byte als Antwort
    public static final int DAPI_STEPPER_RETURN_4_BYTES                     = 0xc0000000;       // Kommando schickt 4 Byte als Antwort

    public static final int DAPI_STEPPER_CMD_SET_MOTORCHARACTERISTIC                = ( 0x00000001 + DAPI_STEPPER_RETURN_0_BYTES );
    public static final int DAPI_STEPPER_CMD_GET_MOTORCHARACTERISTIC                = ( 0x00000002 + DAPI_STEPPER_RETURN_4_BYTES ); 
    public static final int DAPI_STEPPER_CMD_SET_POSITION                           = ( 0x00000003 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_GO_POSITION                            = ( 0x00000004 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_GET_POSITION                           = ( 0x00000005 + DAPI_STEPPER_RETURN_4_BYTES );  
    public static final int DAPI_STEPPER_CMD_SET_FREQUENCY                          = ( 0x00000006 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_SET_FREQUENCY_DIRECTLY                 = ( 0x00000007 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_GET_FREQUENCY                          = ( 0x00000008 + DAPI_STEPPER_RETURN_2_BYTES );  
    public static final int DAPI_STEPPER_CMD_FULLSTOP                               = ( 0x00000009 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_STOP                                   = ( 0x00000010 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_GO_REFSWITCH                           = ( 0x00000011 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_DISABLE                                = ( 0x00000014 + DAPI_STEPPER_RETURN_0_BYTES );  
    public static final int DAPI_STEPPER_CMD_MOTORCHARACTERISTIC_LOAD_DEFAULT       = ( 0x00000015 + DAPI_STEPPER_RETURN_0_BYTES );
    public static final int DAPI_STEPPER_CMD_MOTORCHARACTERISTIC_EEPROM_SAVE        = ( 0x00000016 + DAPI_STEPPER_RETURN_0_BYTES );
    public static final int DAPI_STEPPER_CMD_MOTORCHARACTERISTIC_EEPROM_LOAD        = ( 0x00000017 + DAPI_STEPPER_RETURN_0_BYTES );
    public static final int DAPI_STEPPER_CMD_GET_CPU_TEMP                           = ( 0x00000018 + DAPI_STEPPER_RETURN_1_BYTES );
    public static final int DAPI_STEPPER_CMD_GET_MOTOR_SUPPLY_VOLTAGE               = ( 0x00000019 + DAPI_STEPPER_RETURN_2_BYTES );
    public static final int DAPI_STEPPER_CMD_GO_POSITION_RELATIVE                   = ( 0x00000020 + DAPI_STEPPER_RETURN_0_BYTES );
    public static final int DAPI_STEPPER_CMD_EEPROM_ERASE                           = ( 0x00000021 + DAPI_STEPPER_RETURN_0_BYTES );
    public static final int DAPI_STEPPER_CMD_SET_VECTORMODE                         = ( 0x00000040 + DAPI_STEPPER_RETURN_0_BYTES );  

    // ------------------------------------
    // values for PAR1 for DAPI_STEPPER_CMD_SET_MOTORCHARACTERISTIC
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_STEPMODE                             = 1;    // Schrittmode (Voll-, Halb-, Viertel-, Achtel-, Sechszehntelschritt 
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_GOFREQUENCY                          = 2;    // Schrittfrequenz bei GoPosition [Vollschritt / s]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_STARTFREQUENCY                       = 3;    // Startfrequenz [Vollschritt / s]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_STOPFREQUENCY                        = 4;    // Stopfrequenz [Vollschritt / s]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_MAXFREQUENCY                         = 5;    // maximale Frequenz [Vollschritt / s]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_ACCELERATIONSLOPE                    = 6;    // Beschleunigung in [Vollschritten / ms]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_DECELERATIONSLOPE                    = 7;    // Bremsung in [Vollschritten / ms]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_PHASECURRENT                         = 8;    // Phasenstrom [mA]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_HOLDPHASECURRENT                     = 9;    // Phasenstrom bei Motorstillstand [mA]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_HOLDTIME                             = 10;   // Zeit in der der Haltestrom flie�t nach Motorstop [s]
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_STATUSLEDMODE                        = 11;   // Betriebsart der Status-LED
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_INVERT_ENDSW1                        = 12;   // invertiere Funktion des Endschalter1  
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_INVERT_ENDSW2                        = 13;   // invertiere Funktion des Endschalter12 
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_INVERT_REFSW1                        = 14;   // invertiere Funktion des Referenzschalterschalter1
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_INVERT_REFSW2                        = 15;   // invertiere Funktion des Referenzschalterschalter2
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_INVERT_DIRECTION                     = 16;   // invertiere alle Richtungsangaben
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_ENDSWITCH_STOPMODE                   = 17;   // Bei Endschalter soll (0=full stop/1=stop mit rampe)
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_GOREFERENCEFREQUENCY_TOENDSWITCH     = 18;   // Motor Frequency for GoReferenceCommand
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_GOREFERENCEFREQUENCY_AFTERENDSWITCH  = 19;   // Motor Frequency for GoReferenceCommand
    public static final int DAPI_STEPPER_MOTORCHAR_PAR_GOREFERENCEFREQUENCY_TOOFFSET        = 20;   // Motor Frequency for GoReferenceCommand

    // ----------------------------------------------------------------------------
    // values for PAR1 for DAPI_STEPPER_CMD_GO_REFSWITCH
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_REF1                  = 1;
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_REF2                  = 2;
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_REF_LEFT              = 4;
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_REF_RIGHT             = 8;
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_REF_GO_POSITIVE       = 16;
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_REF_GO_NEGATIVE       = 32;
    public static final int DAPI_STEPPER_GO_REFSWITCH_PAR_SET_POS_0             = 64;

    // ------------------------------------
    // Stepper Read Status
    public static final int DAPI_STEPPER_STATUS_GET_POSITION                    = 0x01;
    public static final int DAPI_STEPPER_STATUS_GET_SWITCH                      = 0x02;
    public static final int DAPI_STEPPER_STATUS_GET_ACTIVITY                    = 0x03;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // CAN-Defines

    // ------------------------------------
    // CAN Commands
    public static final int DAPI_CAN_CMD_SET_BITRATE            = 1;
    public static final int DAPI_CAN_CMD_SET_MASK0              = 2;
    public static final int DAPI_CAN_CMD_SET_RX_ADDRESS         = 3;
    public static final int DAPI_CAN_CMD_CLR_TIMESTAMP          = 4;
    public static final int DAPI_CAN_CMD_GET_BITRATE            = 5;
    public static final int DAPI_CAN_CMD_GET_TIMESTAMP          = 6;

    public static final int DAPI_CAN_CMD_TEST_GEN_RX_PACK       = 0xfffffff0;

    public static final int DAPI_PAR_CAN_MESSAGE_BOX_0          = 0;
    public static final int DAPI_PAR_CAN_MESSAGE_BOX_1          = 1;
    public static final int DAPI_PAR_CAN_MESSAGE_BOX_2          = 2;
    public static final int DAPI_PAR_CAN_MESSAGE_BOX_3          = 3;

    public static final int DAPI_CAN_BITRATE_10000              = 10000;
    public static final int DAPI_CAN_BITRATE_20000              = 20000;
    public static final int DAPI_CAN_BITRATE_50000              = 50000;
    public static final int DAPI_CAN_BITRATE_100000             = 100000;
    public static final int DAPI_CAN_BITRATE_125000             = 125000;
    public static final int DAPI_CAN_BITRATE_250000             = 250000;
    public static final int DAPI_CAN_BITRATE_500000             = 500000;
    public static final int DAPI_CAN_BITRATE_1000000            = 1000000;

    public static final int DAPI_CAN_MASK_SINGLE                = 0xffffffff;
    public static final int DAPI_CAN_MASK_ALL                   = 0x0;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // CNT48 Commands

    public static final int DAPI_CNT48_FILTER_20ns              = 0x0000;
    public static final int DAPI_CNT48_FILTER_100ns             = 0x1000;
    public static final int DAPI_CNT48_FILTER_250ns             = 0x2000;
    public static final int DAPI_CNT48_FILTER_500ns             = 0x3000;
    public static final int DAPI_CNT48_FILTER_1us               = 0x4000;
    public static final int DAPI_CNT48_FILTER_2_5us             = 0x5000;
    public static final int DAPI_CNT48_FILTER_5us               = 0x6000;
    public static final int DAPI_CNT48_FILTER_10us              = 0x7000;
    public static final int DAPI_CNT48_FILTER_25us              = 0x8000;
    public static final int DAPI_CNT48_FILTER_50us              = 0x9000;
    public static final int DAPI_CNT48_FILTER_100us             = 0xA000;
    public static final int DAPI_CNT48_FILTER_250us             = 0xB000;
    public static final int DAPI_CNT48_FILTER_500us             = 0xC000;
    public static final int DAPI_CNT48_FILTER_1ms               = 0xD000;
    public static final int DAPI_CNT48_FILTER_2_5ms             = 0xE000;
    public static final int DAPI_CNT48_FILTER_5ms               = 0xF000;

    public static final int DAPI_CNT48_MODE_COUNT_RISING_EDGE                   = 0x0000;
    public static final int DAPI_CNT48_MODE_COUNT_RISING_EDGE_X2                = 0x0001;
    public static final int DAPI_CNT48_MODE_COUNT_RISING_EDGE_X4                = 0x0002;
    public static final int DAPI_CNT48_MODE_T                                   = 0x000D;
    public static final int DAPI_CNT48_MODE_FREQUENCY                           = 0x000E;
    public static final int DAPI_CNT48_MODE_PWM                                 = 0x000F;

    public static final int DAPI_CNT48_SUBMODE_NO_RESET                         = 0x0000;
    public static final int DAPI_CNT48_SUBMODE_RESET_WITH_READ                  = 0x0010;
    public static final int DAPI_CNT48_SUBMODE_NO_RESET_NO_HW_RESET             = 0x0020;
    public static final int DAPI_CNT48_SUBMODE_RESET_WITH_READ_NO_HW_RESET      = 0x0030;
    public static final int DAPI_CNT48_SUBMODE_RESET_ON_CH_7                    = 0x0070;
    public static final int DAPI_CNT48_SUBMODE_LATCH_COMMON                     = 0x0080;
    public static final int DAPI_CNT48_SUBMODE_FREQUENCY_TIME_BASE_1ms          = 0x0030;
    public static final int DAPI_CNT48_SUBMODE_FREQUENCY_TIME_BASE_10ms         = 0x0020;
    public static final int DAPI_CNT48_SUBMODE_FREQUENCY_TIME_BASE_100ms        = 0x0010;
    public static final int DAPI_CNT48_SUBMODE_FREQUENCY_TIME_BASE_1sec         = 0x0000;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // Software FIFO Commands

    public static final int DAPI_FIFO_TYPE_READ_AD_FIFO                         = 0x01;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // PWM Commands

    public static final int DAPI_PWM_FREQUENCY_10HZ                             = 1;
    public static final int DAPI_PWM_FREQUENCY_100HZ                            = 10;
    public static final int DAPI_PWM_FREQUENCY_250HZ                            = 25;
    public static final int DAPI_PWM_FREQUENCY_1000HZ                           = 100;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // Definitions for DapiOpenEx

    // left out (not possible): DAPI_OPENMODULEEX_STRUCT;

    public static final int DAPI_OPEN_MODULE_OPTION_USE_EXBUFFER                = (1<<0);   // Bit 0
    public static final int DAPI_OPEN_MODULE_OPTION_NO_RESCAN                   = (1<<1);   // Bit 1

    public static final int DAPI_OPEN_MODULE_ENCRYPTION_TYPE_NONE               = 0;
    public static final int DAPI_OPEN_MODULE_ENCRYPTION_TYPE_NORMAL             = 1;
    public static final int DAPI_OPEN_MODULE_ENCRYPTION_TYPE_ADMIN              = 2;
    public static final int DAPI_OPEN_MODULE_ENCRYPTION_TYPE_ADMIN_TEMP         = 3;

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
}

