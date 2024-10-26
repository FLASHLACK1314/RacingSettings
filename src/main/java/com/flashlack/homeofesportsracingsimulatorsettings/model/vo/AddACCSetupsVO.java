package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 添加ACC设置VO
 * 含多个配置界面，包含轮胎、电子元件、燃料及策略等设置
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
@NotNull
public class AddACCSetupsVO {
    private String gameName;
    private String trackName;
    private String carName;
    private String setupsName;
    private AccSetups setups;

    @Data
    @Accessors(chain = true)
    public static class AccSetups {
        private TireSettings tireSettings;
        private ElectronicComponents electronicComponents;
        private FuelAndStrategy fuelAndStrategy;
        private MechanicalGrip mechanicalGrip;
        private SuspensionDamping suspensionDamping;
        private Aerodynamics aerodynamics;

        @Data
        @Accessors(chain = true)
        // 轮胎设置界面
        public static class TireSettings {
            private TirePressure pressure;    // 胎压设置
            private ToeAlignment toe;         // 束脚设置
            private CamberAngle camber;       // 外倾角设置
            private CasterAngle caster;       // 后倾角设置

            @Data
            @Accessors(chain = true)
            // 轮胎胎压（单位：psi）
            public static class TirePressure {
                private Double leftFront;    // 左前胎压
                private Double rightFront;   // 右前胎压
                private Double leftRear;     // 左后胎压
                private Double rightRear;    // 右后胎压
            }

            @Data
            @Accessors(chain = true)
            // 轮胎束脚（单位：度）
            public static class ToeAlignment {
                private Double leftFront;    // 左前束脚
                private Double rightFront;   // 右前束脚
                private Double leftRear;     // 左后束脚
                private Double rightRear;    // 右后束脚
            }

            @Data
            @Accessors(chain = true)
            // 轮胎外倾角（单位：度）
            public static class CamberAngle {
                private Double leftFront;    // 左前外倾角
                private Double rightFront;   // 右前外倾角
                private Double leftRear;     // 左后外倾角
                private Double rightRear;    // 右后外倾角
            }

            @Data
            @Accessors(chain = true)
            // 轮胎后倾角（单位：度）
            public static class CasterAngle {
                private Double leftFront;    // 左前后倾角
                private Double rightFront;   // 右前后倾角
            }
        }

        @Data
        @Accessors(chain = true)
        // 电子元件界面
        public static class ElectronicComponents {
            private Double tcLevel;          // 牵引力控制系统等级 (TC)
            private Double absLevel;         // 防抱死刹车系统等级 (ABS)
            private Double ecuMap;           // 赛车引擎模式 (ECU Map)
            private Double telemetryLap;     // 遥测圈数
            private Double tcLevel2;         // 牵引力控制系统等级2 (TC2)
        }

        @Data
        @Accessors(chain = true)
        // 燃料和策略界面
        public static class FuelAndStrategy {
            private Double fuelAmount;       // 燃料量 (单位：L)
            private String tireCompound;      // 轮胎种类 (干/湿)
            private String tireSettings;     // 轮胎设置
            private Double frontBrake;       // 前刹车
            private Double rearBrake;        // 后刹车
        }

        @Data
        @Accessors(chain = true)
        // 机械抓地力界面
        public static class MechanicalGrip {
            private Double frontAntiRollBar;     // 前防侧摆杆 (Anti-roll bar)
            private Double brakePower;           // 制动功率 (单位：%)
            private Double brakeBias;            // 刹车偏移 (单位：%)
            private Double steerRatio;           // 转向传动比
            private SuspensionSettings suspension;   // 悬架设置
            private Double rearAntiRollBar;      // 后防侧摆杆 (Anti-roll bar)
            private Double preloadDifferential;  // 预紧差速锁 (Preload differential)

            @Data
            @Accessors(chain = true)
            // 悬架设置
            public static class SuspensionSettings {
                private WheelRate wheelRate;               // 悬架刚度
                private BumpStopRate bumpStopRate;         // 缓冲挡块率
                private BumpStopRange bumpStopRange;       // 缓冲挡块范围
                @Data
                @Accessors(chain = true)
                // 悬架刚度
                public static class WheelRate {
                    private Double leftFront;       // 左前悬架刚度
                    private Double rightFront;      // 右前悬架刚度
                    private Double leftRear;        // 左后悬架刚度
                    private Double rightRear;       // 右后悬架刚度
                }
                @Data
                @Accessors(chain = true)
                // 缓冲挡块率
                public static class BumpStopRate {
                    private Double leftFront;       // 左前缓冲挡块率
                    private Double rightFront;      // 右前缓冲挡块率
                    private Double leftRear;        // 左后缓冲挡块率
                    private Double rightRear;       // 右后缓冲挡块率
                }
                @Data
                @Accessors(chain = true)
                // 缓冲挡块范围
                public static class BumpStopRange {
                    private Double leftFront;       // 左前缓冲挡块范围
                    private Double rightFront;      // 右前缓冲挡块范围
                    private Double leftRear;        // 左后缓冲挡块范围
                    private Double rightRear;       // 右后缓冲挡块范围
                }
            }

        }

        @Data
        @Accessors(chain = true)
        // 减震器界面
        public static class SuspensionDamping {
            private Bump bump;               // 冲击减震器
            private FastBump fastBump;       // 快速冲击减震器
            private Rebound rebound;         // 回弹减震器
            private FastRebound fastRebound; // 快速回弹减震器

            @Data
            @Accessors(chain = true)
            // 冲击减震器设置
            public static class Bump {
                private Double leftFront;     // 左前
                private Double rightFront;    // 右前
                private Double leftRear;      // 左后
                private Double rightRear;     // 右后
            }

            @Data
            @Accessors(chain = true)
            // 快速冲击减震器设置
            public static class FastBump {
                private Double leftFront;     // 左前
                private Double rightFront;    // 右前
                private Double leftRear;      // 左后
                private Double rightRear;     // 右后
            }

            @Data
            @Accessors(chain = true)
            // 回弹减震器设置
            public static class Rebound {
                private Double leftFront;     // 左前
                private Double rightFront;    // 右前
                private Double leftRear;      // 左后
                private Double rightRear;     // 右后
            }

            @Data
            @Accessors(chain = true)
            // 快速回弹减震器设置
            public static class FastRebound {
                private Double leftFront;     // 左前
                private Double rightFront;    // 右前
                private Double leftRear;      // 左后
                private Double rightRear;     // 右后
            }
        }

        @Data
        @Accessors(chain = true)
        // 气动设置
        public static class Aerodynamics {
            private Double frontRideHeight;     // 前底盘高度 (单位：mm)
            private Double frontSplitter;       // 前扰流器
            private Double frontBrakeDuct;      // 前刹车管

            private Double rearRideHeight;      // 后底盘高度 (单位：mm)
            private Double rearWing;            // 尾翼
            private Double rearBrakeDuct;       // 后刹车管
        }
    }
}
