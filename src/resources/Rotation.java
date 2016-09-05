package resources;

public class Rotation {
	
	/*
	 * Code resource:
	 * https://bitbucket.org/mkuttel/carbbuilderii/src/c6e583aab3bf7189c02a4f9060feff77190a6688/CBIIsource/Rotation.cs?at=default&fileviewer=file-view-default
	 */
	
	
	/*
	 * General order of how rotation works
	 * get angle between the vectors  e.g. double currentAngle = Rotation.getAngleBetweenVector(stationaryAtom,parentO,parentC);
	 * find the angle to rotate e.g. double angle2rotate = currentAngle - Rotation.getRad(60);
	 * work out the normal from 3 points e.g. double[] normal = Rotation.getNormal(stationaryAtom,parentO,parentC);
	 * find the new point e.g. double[] xyz2 = Rotation.getNewPointFromNormal(parentO, normal, angle2rotate, stationaryAtom);
	 * set the new position e.g. parentNode.setAtomPosition ("HN", 0, xyz2); //unrotate H in NAc
	 */
	
	 /*
    Rotate a point from point A which is on the normal, it uses getNewPoint method
    "pta"> xyz coordinates on the normal
    "normal">double[], normal vector
    "theta">double, the angle in Rad to rotate to
    "pt">double[], the old xyz coordinates
     return updated xyz coordinates
     */
    public static double[] getNewPointFromNormal(double[] pta,double[] normal, double theta, double[] pt)
    {
        // 1. work out ptb on normal using pta and normal
        // ptb = {pta + t*normal}
        // let t = 3;
        double[] ptb = { pta[0] + 3 * normal[0], pta[1] + 3 * normal[1], pta[2] + 3 * normal[2] };

        double [] q = getNewPoint(pta,ptb,theta,pt);

        return q;
    }
	
	
    /*"pta"> point A to form the vector
      "ptb"> point B to form the vector
      "theta" the angle in Rad to rotate to
      "pt" the old xyz coordinates</param>
       returns  updated xyz coordinates
     */
    public static double[] getNewPoint(double[] pta, double[] ptb, double theta, double[] pt)
    {
        
        // get the vector from the two points A and B
        double[] vector = { pta[0] - ptb[0], pta[1] - ptb[1], pta[2] - ptb[2] };
        // normalise the vector 
        double normal = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2));
        // rotation axis unit vector
        vector[0] = vector[0] / normal;
        vector[1] = vector[1] / normal;
        vector[2] = vector[2] / normal;

        // translate so axis is at orgin according to point A
        pt[0] -= pta[0];
        pt[1] -= pta[1];
        pt[2] -= pta[2];

        // rotate
        double[] q = getRotated(vector, pt, theta);

        q[0] += pta[0];
        q[1] += pta[1];
        q[2] += pta[2];
        return q;
    }

    /*
    Use matrix to calculate the new coordinate by rotating the pt about the vector
    "vector">double[], the vector
    "pt">double[], the old point
    "theta">double, angle in Rad
    returns updated coordinate
     */
    private static double[] getRotated(double[] vector, double[] pt, double theta) 
    {
        double[] q = { 0, 0, 0 };
        double costheta = Math.cos(theta);
        double sintheta = Math.sin(theta);

        // rotate
        q[0] += (costheta + (1 - costheta) * vector[0] * vector[0]) * pt[0];
        q[0] += ((1 - costheta) * vector[0] * vector[1] - vector[2] * sintheta) * pt[1];
        q[0] += ((1 - costheta) * vector[0] * vector[2] + vector[1] * sintheta) * pt[2];

        q[1] += ((1 - costheta) * vector[0] * vector[1] + vector[2] * sintheta) * pt[0];
        q[1] += (costheta + (1 - costheta) * vector[1] * vector[1]) * pt[1];
        q[1] += ((1 - costheta) * vector[1] * vector[2] - vector[0] * sintheta) * pt[2];

        q[2] += ((1 - costheta) * vector[0] * vector[2] - vector[1] * sintheta) * pt[0];
        q[2] += ((1 - costheta) * vector[1] * vector[2] + vector[0] * sintheta) * pt[1];
        q[2] += (costheta + (1 - costheta) * vector[2] * vector[2]) * pt[2];

        return q;
    
    }
   
    /*
    Calculate the angle between 2 vectors made up of 3 points
    "pt1">double[], point 1 on the vector
    "pt2">double[], point 2 on the vector
    "pt3">double[], point 3 on the vector
    returns angle between vector
     */
    public static double getAngleBetweenVector(double[] pt1,double[] pt2,double[] pt3) 
    {
        double[] vector1 = { pt1[0] - pt2[0], pt1[1] - pt2[1], pt1[2] - pt2[2] };
        double[] vector2 = { pt2[0] - pt3[0], pt2[1] - pt3[1], pt2[2] - pt3[2] };
        double angleBetweenVector = getAngleBetweenNormal(vector1,vector2);
        if (angleBetweenVector == 0.0) // for the mirror image case
            angleBetweenVector = 0.0001;
        return angleBetweenVector;
    
    }
    
    /*
    Calculate the normal of the plane made up of 3 points
   "point1">double[], point 1 on the vector
    "point2">double[], point 2 on the vector
    "point3">double[], point 3 on the vector
    returns the normal of the plane
     */
    public static double[] getNormal(double[] point1, double[] point2, double[] point3)
    {
        double[] normal = new double[3];
        // A = y1(z2-z3) + y2(z3-z1) + y3(z1-z2)
        normal[0] = point1[1] * (point2[2] - point3[2]) + point2[1] * (point3[2] - point1[2]) + point3[1] * (point1[2] - point2[2]);
        // B = z1(x2-x3) + z2(x3-x1) + z3(x1-x2)
        normal[1] = point1[2] * (point2[0] - point3[0]) + point2[2] * (point3[0] - point1[0]) + point3[2] * (point1[0] - point2[0]);
        // C = x1(y2-y3) + x2(y3-y1) + x3(y1-y2)
        normal[2] = point1[0] * (point2[1] - point3[1]) + point2[0] * (point3[1] - point1[1]) + point3[0] * (point1[1] - point2[1]);
        if (normal[0] == 0.0 && normal[1] == 0.0 && normal[2] == 0.0) // for the mirror image case
        {
            normal[0] = 0.0001;
            normal[1] = 0.0001;
            normal[2] = 0.0001;
        }
        return normal;
    }
    
    /*
    Calculate angle between 2 normals, by
    cos(Angle) = dot product / cross product of 2 normals.
    for normal = Ai + Bj + Ck,
    dot product = A1A2+B1B2+C1C2
    normal product = sqrt(n1^2) . sqrt(n2^2) 
    "normal1">double[], normal 1
    "normal2">double[], normal 2
    returnsthe angle in Rad
     */
    public static double getAngleBetweenNormal(double[] normal1, double[] normal2)
    {
        double angle = 0;
        // dot product
        double dotProduct = normal1[0] * normal2[0] + normal1[1] * normal2[1] + normal1[2] * normal2[2];
        // cross product
        double first = Math.sqrt(Math.pow(normal1[0], 2) + Math.pow(normal1[1], 2) + Math.pow(normal1[2], 2));
        double second = Math.sqrt(Math.pow(normal2[0], 2) + Math.pow(normal2[1], 2) + Math.pow(normal2[2], 2));
        double crossProduct = first * second;
		double tmp = dotProduct / crossProduct;
		if (tmp < -1)
			tmp = -1; //dealing with limits of function - lovely overflow error otherwise when 180deg!
		else if (tmp > 1)
			tmp = 1;// dealing with limits
        angle = Math.acos(tmp);
	/*  //DEBUG for limits....	
	 * Console.WriteLine ("\n...[Rotation.getAngleBetweenNorma]: \ndotProduct=" + dotProduct + "\nfirst=" + first + 
		                   "\nsecond =" + second + "\ncrossProduct =" + crossProduct + "\nAngle =" + angle );
		Console.WriteLine ("\n...[Rotation.getAngleBetweenNorma]: ACos (-1) =" + Math.Acos(-1) + " tmp=-1?" + (tmp==-1));*/
       
        return angle;

    }

    /*
    Calculate the sign of angle, using algorithm from:
    The Rainey Lab - Dihedral angle calculation,
    http://structbio.biochem.dal.ca/jrainey/dihedralcalc.html
    where, sign = sign(n2·(pt2-pt1)) =
    sign of dot product of (normal 2 * vector12)
    "angle">double, old angle
    "normal2">double[] the other normal
    "point1">double[], point 1
   	"point2">double[], point 2
    returns the signed angle in Rad
     * 
     */
    public static double getSigned(double angle, double[] normal2, double[] point1, double[] point2) 
    {
        double[] vector = { point2[0] - point1[0], point2[1] - point1[1], point2[2] - point1[2] };
        double sign = normal2[0] * vector[0] + normal2[1] * vector[1] + normal2[2] * vector[2];
        if (sign < 0)
            angle = -angle;
        return angle;
    }

    /*
    Convert degree to rad
    "degree">double, angle in degree
    returns angle in rad
     */
    public static double getRad(double degree) 
    {
        return Math.PI * degree / 180;
    }

    /*
    /// Convert rad to degree
    /// <param name="radian">double, angle in rad
    returns angle in degrees
    */
    public static double getDeg(double radian)
    {
        return 180 / Math.PI * radian;
    }

	// MK removed for now - breaks algorithm!!!!!
    /// <summary>
    /// Give more options for angles so that when after all testing with angles provided, 
    /// by incrementing 20 each step,
    /// This gives more torsion angle pairs to see if collide
    /// </summary>
    /// <param name="counter">int, the counter for increment</param>
    /// <returns>double[], phi, psi, and omega angle in degree</returns>
    public static double[] getAppendAngles(int counter)
    {
        double[] append = new double[3];
        append[0] = counter % 18 * 20 + 1;
        append[1] = 1; 
        append[2] = counter % 18 * 20 + 1;

        return append;
    }

}


