package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * F1 2021赛车设置DTO
 *
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class F124SetupsDTO {
    private Aerodynamics aerodynamics;
    private Gearbox gearbox;
    private SuspensionGeometry suspensionGeometry;
    private Suspension suspension;
    private Brakes brakes;
    private Tires tires;

    @Data
    @Accessors(chain = true)
    // 空气动力学设计
    public static class Aerodynamics {

        private Integer frontWing; // 前翼启动设计（0-50）


        private Integer rearWing;  // 尾翼启动设计（0-50）
    }

    @Data
    @Accessors(chain = true)
    // 变速箱
    public static class Gearbox {

        private Integer throttleDifferentialAdjustment; // 油门差速器调节（10%-100%）


        private Integer offThrottleDifferentialAdjustment; // 非油门差速器调节（10%-100%）


        private Integer engineBraking; // 引擎制动（0%-100%）
    }

    @Data
    @Accessors(chain = true)
    // 悬挂几何设计
    public static class SuspensionGeometry {

        private Double frontCamber; // 前轮垂直倾角（-3.50°到-2.50°）


        private Double rearCamber;  // 后轮垂直倾角（-2.20°到-0.70°）


        private Double frontToe;    // 前轮后束脚（0.00°到0.50°）


        private Double rearToe;     // 后轮后束脚（0.00°到0.50°）
    }

    @Data
    @Accessors(chain = true)
    // 悬挂
    public static class Suspension {

        private Integer frontSuspension; // 前悬挂（1-41）


        private Integer rearSuspension;  // 后悬挂（1-41）


        private Integer frontAntiRollBar; // 前防倾杆（1-21）


        private Integer rearAntiRollBar;  // 后防倾杆（1-21）


        private Integer frontRideHeight;  // 前轮车身高度（10-40）


        private Integer rearRideHeight;   // 后轮车身高度（40-100）
    }

    @Data
    @Accessors(chain = true)
    // 刹车
    public static class Brakes {

        private Integer brakePressure; // 刹车压力（80%-100%）


        private Integer frontBrakeBias; // 前轮刹车分配（50%-70%）
    }

    @Data
    @Accessors(chain = true)
    // 轮胎
    public static class Tires {

        private Double rightFrontPressure; // 右前轮压力（22.5-29.5 PSI）


        private Double leftFrontPressure;  // 左前轮压力（22.5-29.5 PSI）


        private Double rightRearPressure;  // 右后轮压力（20.5-26.5 PSI）


        private Double leftRearPressure;   // 左后轮压力（20.5-26.5 PSI）
    }
}