package goldiounes.com.vn.controllers;

import goldiounes.com.vn.models.dtos.DiamondDTO;
import goldiounes.com.vn.models.dtos.DiamondDetailDTO;
import goldiounes.com.vn.services.DiamondDetailService;
import goldiounes.com.vn.services.DiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diamond-management")
public class DiamondController {

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private DiamondDetailService diamondDetailService;

    @PostMapping("/diamonds")
    public DiamondDTO createDiamond(@RequestBody DiamondDTO diamondDTO) {
        return diamondService.createDiamond(diamondDTO);
    }

    @GetMapping("/diamonds")
    public List<DiamondDTO> getAllDiamonds() {
        return diamondService.findAll();
    }

    @GetMapping("/diamonds/{id}")
    public DiamondDTO getDiamond(@PathVariable int id) {
        return diamondService.findById(id);
    }

    @PutMapping("/diamonds/{id}")
    public DiamondDTO updateDiamond(@PathVariable int id, @RequestBody DiamondDTO diamondDTO) {
        return diamondService.updateDiamond(id, diamondDTO);
    }

    @DeleteMapping("/diamonds/{id}")
    public void deleteDiamond(@PathVariable int id) {
        diamondService.deleteDiamond(id);
    }

    @PostMapping("/diamond-details")
    public DiamondDetailDTO createDiamondDetail(@RequestBody DiamondDetailDTO diamondDetailDTO) {
        return diamondDetailService.createDiamondDetail(diamondDetailDTO);
    }

    @GetMapping("/diamond-details")
    public List<DiamondDetailDTO> getAllDiamondDetails() {
        return diamondDetailService.findAll();
    }

    @GetMapping("/diamond-details/{id}")
    public DiamondDetailDTO getDiamondDetail(@PathVariable int id) {
        return diamondDetailService.findById(id);
    }

    @DeleteMapping("/diamond-details/{id}")
    public void deleteDiamondDetail(@PathVariable int id) {
        diamondDetailService.deleteById(id);
    }

    @PutMapping("/diamonds-details/{id}")
    public DiamondDetailDTO updateDiamondDetail(@PathVariable int id, @RequestBody DiamondDetailDTO diamondDetailDTO) {
        return diamondDetailService.update(id, diamondDetailDTO);
    }
}
