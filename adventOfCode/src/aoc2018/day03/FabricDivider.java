package aoc2018.day03;

import java.util.ArrayList;
import java.util.List;

import aoc2018.AbstractAocAlgorithm;

public class FabricDivider extends AbstractAocAlgorithm {

	private List<FabricClaim> fabricClaims;
	
	private int[][] fabric;

	private int overlappingClaimedSquares;

	
	// Challenge A, first try (for debugging (on second thought, debugging is hard with a 
	// 1000 * 1000 square)): 110383 (correct without debugging).
	
	public FabricDivider(boolean isCommandLineFine) {
		super(isCommandLineFine);
	}

	public boolean initialize() {
		if (!super.initialize(2018, 3)) {
			return false;
		}
		overlappingClaimedSquares = 0;
		fabricClaims = new ArrayList<>();
		for (String input : fetcher.getInputLines()) {
			FabricClaim claim = new FabricClaim(input);
			fabricClaims.add(claim);
		}
		
		fabric = new int[1000][1000];
		
		return true;
	}
	
	public void applyAllClaims() {
		for (FabricClaim claim : fabricClaims) {
			applyClaim(claim);			
		}
	}
	
	
	private void applyClaim(FabricClaim claim) {
		int startX = claim.getLeftUpperCorner()[0];
		int startY = claim.getLeftUpperCorner()[1];
		
		int width = claim.getSize()[0];
		int height = claim.getSize()[1];
		
		for (int i=0; i<width; i++) {
			
			for (int j=0; j<height; j++) {
				
				if (fabric[startX + i][startY + j] == -1) {
					// An overlap already occurred on this square. Update the flag.
					claim.setDoesNotOverlap(false);
					continue;
				}

				if (fabric[startX + i][startY + j] != 0) {
					// Overlap alert! This square has already been claimed by another elf.
					overlappingClaimedSquares++;
					claim.setDoesNotOverlap(false);
					fabricClaims.get(fabric[startX + i][startY + j] - 1).setDoesNotOverlap(false);

					// Set this square to -1, so it won't be considered in further overlapping.
					fabric[startX + i][startY + j] = -1;
				} else {
					fabric[startX + i][startY + j] = claim.getId();
				}
			}
		}
	}	
	
	public int getOverlapFreeClaimId() {
		int claimId = 0;
		for (FabricClaim claim : fabricClaims) {
			if (claim.doesNotOverlap()) {
				claimId = claim.getId();
				break;
			}
		}
		return claimId;
	}

	public int getOverlappingClaimedSquares() {
		return overlappingClaimedSquares;
	}
}
